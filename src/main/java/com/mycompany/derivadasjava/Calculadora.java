/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.derivadasjava;

import static com.mycompany.derivadasjava.DerivadasJava.reglaDeConstante;
import static com.mycompany.derivadasjava.DerivadasJava.reglaDePotencia;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author javiz
 */
public class Calculadora {
    
    // Metodo para formatear coeficientes de potencias
    public static String formatearCoef(double coef) {
        if (coef == 1.0) return "";
        if (coef == -1.0) return "-";
        if (coef == (int) coef) return String.valueOf((int) coef);
        return String.valueOf(coef); // Para decimales
    }
    
    // Metodo para resolver el denominador
    public static String resolverDenominador(String denominador) {

        // Limpiamos
        String den = denominador.replaceAll("\\s+", "");

        // Aseguramos que todos los términos tengan signo para poder separar correctamente
        if (den.charAt(0) != '-') den = "+" + den;

        // Separamos términos y los metemos en un arreglo
        List<String> terminos = new ArrayList<>();
        StringBuilder actual = new StringBuilder();
        for (int i = 0; i < den.length(); i++) {
            char caracter = den.charAt(i);
            if (caracter != '(' && caracter != ')') {
                if ((caracter == '+' || caracter == '-') && actual.length() > 0) {
                    terminos.add(actual.toString());
                    actual = new StringBuilder();
                }
                actual.append(caracter);
            }
        }
        terminos.add(actual.toString()); // Agrega el último término

        // Resultado acumulado
        StringBuilder resultado = new StringBuilder();

        for (String termino : terminos) {

            termino = termino.trim();
            if (termino.isEmpty()) continue;

            double coef = 1;
            int exponente = 1;

            // Detectar el signo
            boolean negativo = termino.charAt(0) == '-';
            if (negativo || termino.charAt(0) == '+') {
                termino = termino.substring(1);
            }

            // Si es una constante numérica (sin 'x')
            if (!termino.contains("x")) {
                coef = Double.parseDouble(termino);
                if (negativo) coef *= -1;
                double nuevoCoef = coef * coef;
                resultado.append("+").append(nuevoCoef);
                continue;
            }

            // Extraer coeficiente (antes de 'x')
            int indexX = termino.indexOf("x");
            String coefStr = termino.substring(0, indexX);
            if (coefStr.isEmpty()) {
                coef = 1; // Implícito
            } else {
                coef = Double.parseDouble(coefStr);
            }

            if (negativo) coef *= -1;

            // Extraer exponente si hay
            if (termino.contains("^")) {
                String expStr = termino.substring(termino.indexOf("^") + 1);
                exponente = Integer.parseInt(expStr);
            }

            double nuevoCoef = coef * coef;
            int nuevoExp = exponente * 2;

            resultado.append("+").append(nuevoCoef).append("x^").append(nuevoExp);
        }

        // Eliminar '+' inicial si existe y el coeficiente en caso de ser 1
        String finalResultado = resultado.toString();
        if (finalResultado.startsWith("+")) {
            finalResultado = finalResultado.substring(1);
        }
        
        if (finalResultado.startsWith("1")) {
            finalResultado = finalResultado.substring(3);
        }

        return finalResultado;
    }
    
    // Metodo para construir una cadena de mayor a menor grado
    public static String construirResultadoOrdenado(Map<String, Double> mapa) {
        List<String> variablesOrdenadas = new ArrayList<>(mapa.keySet());

        // Ordenar por grado (de mayor a menor)
        variablesOrdenadas.sort((a, b) -> {
            int gradoA = a.matches("x\\^\\d+") ? Integer.parseInt(a.substring(2)) :
                         (a.equals("x") ? 1 : 0);
            int gradoB = b.matches("x\\^\\d+") ? Integer.parseInt(b.substring(2)) :
                         (b.equals("x") ? 1 : 0);
            return Integer.compare(gradoB, gradoA);
        });

        StringBuilder resultado = new StringBuilder();
        for (String var : variablesOrdenadas) {
            double coef = mapa.get(var);
            if (coef == 0) continue;

            if (resultado.length() > 0 && coef > 0) resultado.append("+");

            if (coef == 1 && !var.isEmpty()) resultado.append(var);
            else if (coef == -1 && !var.isEmpty()) resultado.append("-").append(var);
            else resultado.append(coef).append(var);
        }

        return resultado.length() == 0 ? "0" : resultado.toString();
    }
    
    // Metodo para multiplicar dos funciones entre si
    public static String multiplicarFunciones(String f1, String f2) {
        f1 = f1.replaceAll("\\s+", "");
        f2 = f2.replaceAll("\\s+", "");

        List<String> terminosF1 = new ArrayList<>();
        Matcher m1 = Pattern.compile("[+-]?[^+-]+").matcher(f1);
        while (m1.find()) terminosF1.add(m1.group());

        List<String> terminosF2 = new ArrayList<>();
        Matcher m2 = Pattern.compile("[+-]?[^+-]+").matcher(f2);
        while (m2.find()) terminosF2.add(m2.group());

        Map<String, Double> mapa = new HashMap<>();

        for (String t1 : terminosF1) {
            Matcher match1 = Pattern.compile("([+-]?\\d*\\.?\\d*)([a-zA-Z][\\^\\d]*)?").matcher(t1);
            if (!match1.matches()) continue;

            String coefStr1 = match1.group(1);
            String var1 = match1.group(2) == null ? "" : match1.group(2);
            double coef1 = (coefStr1.equals("+") || coefStr1.isEmpty()) ? 1 :
                           (coefStr1.equals("-") ? -1 : Double.parseDouble(coefStr1));

            for (String t2 : terminosF2) {
                Matcher match2 = Pattern.compile("([+-]?\\d*\\.?\\d*)([a-zA-Z][\\^\\d]*)?").matcher(t2);
                if (!match2.matches()) continue;

                String coefStr2 = match2.group(1);
                String var2 = match2.group(2) == null ? "" : match2.group(2);
                double coef2 = (coefStr2.equals("+") || coefStr2.isEmpty()) ? 1 :
                               (coefStr2.equals("-") ? -1 : Double.parseDouble(coefStr2));

                double nuevoCoef = coef1 * coef2;
                String nuevaVar = combinarVariables(var1, var2);

                mapa.put(nuevaVar, mapa.getOrDefault(nuevaVar, 0.0) + nuevoCoef);
            }
        }

        return construirResultadoOrdenado(mapa);
    }

    // Metodo para combinar terminos con el mismo coeficiente y misma literal
    public static String combinarVariables(String var1, String var2) {
        if (var1.isEmpty()) return var2;
        if (var2.isEmpty()) return var1;

        // Solo considera multiplicación de potencias de la misma base
        Pattern p = Pattern.compile("x(?:\\^(\\d+))?");
        Matcher m1 = p.matcher(var1);
        Matcher m2 = p.matcher(var2);

        if (m1.matches() && m2.matches()) {
            int exp1 = m1.group(1) == null ? 1 : Integer.parseInt(m1.group(1));
            int exp2 = m2.group(1) == null ? 1 : Integer.parseInt(m2.group(1));
            int suma = exp1 + exp2;
            return suma == 1 ? "x" : "x^" + suma;
        }

        // Si son distintas, concatenar simbólicamente
        return var1 + "*" + var2;
    }
    
    // Método para derivar la función
    public static String derivarFuncion( String funcion ) {
        try {
            // Limpiamos la funcion
            funcion = funcion.replace( " ", "" );

            // Aseguramos que todos los términos tengan signo para poder dividir correctamente
            if ( funcion.charAt( 0 ) != '-' ) funcion = "+" + funcion;

            // Separamos términos y los metemos en un arreglo
            List<String> terminos = new ArrayList<>();
            StringBuilder actual = new StringBuilder();
            for ( int i = 0; i < funcion.length(); i++ ) {
                char caracter = funcion.charAt( i );
                if ( ( caracter == '+' || caracter == '-' ) && actual.length() > 0 ) {
                    terminos.add( actual.toString() ); // Guarda el término construido hasta ahora
                    actual = new StringBuilder(); // Limpia "actual" para empezar a construir el siguiente término
                }
                actual.append( caracter );
            }
            terminos.add( actual.toString() );

            // Creamos variable para almacenar la derivada de cada termino
            StringBuilder resultado = new StringBuilder();
            
            // Derivar cada término
            for ( String termino : terminos ) {
                termino = termino.trim(); // Elimina espacios en blanco
                if ( termino.isEmpty() ) continue; // Si el termino esta vacio lo salta y sigue con el siguiente

                String derivado = "";
                if ( !termino.contains( "x" ) ) {
                    // Es constante, derivada = 0
                    derivado = "";
                } else {
                    String sinConst = reglaDeConstante( termino );
                    derivado = reglaDePotencia( sinConst );

                    // Recuperar signo
                    if ( termino.charAt( 0 ) == '-' && !derivado.startsWith( "-" ) ) {
                        derivado = "-" + derivado;
                    } else if ( termino.charAt( 0 ) == '+' && !derivado.startsWith( "-" ) ) {
                        derivado = "+" + derivado;
                    } else if ( !derivado.startsWith( "-" ) && !derivado.startsWith( "+" ) ) {
                        derivado = "+" + derivado;
                    }
                }

                resultado.append( derivado ); // Se guardan los resultados en el arreglo por termino
            }

            // Limpieza final: eliminar primer '+' si lo hay
            String finalResultado = resultado.toString();
            if ( finalResultado.startsWith( "+" ) ) {
                finalResultado = finalResultado.substring( 1 );
            }

            return finalResultado;

        } catch ( Exception e ) {
            return "Error en derivada.";
        }
    }
    
    // Metodo para encontrar las constantes en una funcion
    public static String encontrarConstantes( String funcion ){
        
        // Creamos la variable para almacenar la constante
        String constante = "";

        // Quitamos espacios
        funcion = funcion.replace( " ", "" );

        // Expresión regular para capturar cada término
        Pattern pattern = Pattern.compile( "[+-]?[^+-]+()" );
        Matcher matcher = pattern.matcher( funcion );

        while ( matcher.find() ) {
            String termino = matcher.group();
            // Si el término NO contiene la variable, lo consideramos constante
            if ( !termino.contains( "x" ) ) {
                constante = termino;
            }
        }
        
        return constante;
    }
    
    // Metodo para encontrar las potencias por ejemplo: 4x^2
    public static String encontrarPotencias( String funcion ) {
        
        String potencia = "";

        // Eliminamos espacios
        funcion = funcion.replace( " ", "" ); 

        // Detectamos potencias con coeficientes
        Pattern pattern = Pattern.compile( "[-+]?\\d*\\.?\\d*x\\^\\d+" );
        Matcher matcher = pattern.matcher( funcion );

        while ( matcher.find() ) {
            potencia = matcher.group();
        }

        return potencia;
    }
    
    // Metodo para encontrar potencias elevadas a 1 por ejemplo 5x
    public static String encontrarPotenciaSinExponente ( String funcion ){
        
        String potencia = "";

        // Eliminamos espacios
        funcion = funcion.replace( " ", "" ); 

        // Detectamos potencias a la 1 con coeficientes
        Pattern pattern = Pattern.compile( "[-+]?\\d*\\.?\\d*x(?!\\^)" );
        Matcher matcher = pattern.matcher( funcion );

        while ( matcher.find() ) {
            potencia = matcher.group();
        }
        
        return potencia;
    }
    
}
