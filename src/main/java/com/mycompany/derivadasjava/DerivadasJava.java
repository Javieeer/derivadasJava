
package com.mycompany.derivadasjava;

/**
 *
 * @author javiz
 */

import static com.mycompany.derivadasjava.Calculadora.construirResultadoOrdenado;
import static com.mycompany.derivadasjava.Calculadora.encontrarConstantes;
import static com.mycompany.derivadasjava.Calculadora.formatearCoef;
import static com.mycompany.derivadasjava.Calculadora.multiplicarFunciones;
import static com.mycompany.derivadasjava.Calculadora.resolverDenominador;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

public class DerivadasJava {

    //Menu principal
    public static void main(String[] args) {
        MenuPrincipal menuPrincipal = new MenuPrincipal(); // Muestra el menú
    }
    
    // Metodo para menu de una sola derivada
    public static void ventanaDerivadasIndividuales() {
        DerivadasIndividuales derivadasIndividuales = new DerivadasIndividuales();
    }

    // Metodo para menu de dos derivadas
    public static void ventanaDerivadasDobles(){
        DerivadasDobles derivadasDobles = new DerivadasDobles();
    }
    
    // Metodo para la regla de adicion entre dos funciones
    public static String reglaSum(String derivadaFx, String derivadaGx){ // f'(x)+g'(x)
        
        String fx = derivadaFx.replaceAll("\\s+", "");
        String gx = derivadaGx.replaceAll("\\s+", "");

        List<String> terminosFx = new ArrayList<>();
        Matcher m1 = Pattern.compile("[+-]?[^+-]+").matcher(fx);
        while (m1.find()) terminosFx.add(m1.group());

        List<String> terminosGx = new ArrayList<>();
        Matcher m2 = Pattern.compile("[+-]?[^+-]+").matcher(gx);
        while (m2.find()) terminosGx.add(m2.group());

        Map<String, Double> mapa = new HashMap<>();

        // Sumar coeficientes de fx
        for (String termino : terminosFx) {
            Matcher match = Pattern.compile("([+-]?\\d*\\.?\\d*)([a-zA-Z][\\^\\d]*)?").matcher(termino);
            if (match.matches()) {
                String coefStr = match.group(1);
                String var = match.group(2) == null ? "" : match.group(2);
                double coef = (coefStr.equals("+") || coefStr.isEmpty()) ? 1 :
                              (coefStr.equals("-") ? -1 : Double.parseDouble(coefStr));
                mapa.put(var, mapa.getOrDefault(var, 0.0) + coef);
            }
        }

        // Sumar coeficientes de gx
        for (String termino : terminosGx) {
            Matcher match = Pattern.compile("([+-]?\\d*\\.?\\d*)([a-zA-Z][\\^\\d]*)?").matcher(termino);
            if (match.matches()) {
                String coefStr = match.group(1);
                String var = match.group(2) == null ? "" : match.group(2);
                double coef = (coefStr.equals("+") || coefStr.isEmpty()) ? 1 :
                              (coefStr.equals("-") ? -1 : Double.parseDouble(coefStr));
                mapa.put(var, mapa.getOrDefault(var, 0.0) + coef);
            }
        }

        return construirResultadoOrdenado(mapa);
    }
    
    // Metodo para la regla de diferencia entre dos funciones
    public static String reglaDif(String derivadaFx, String derivadaGx){ // f'(x)-g'(x)
        
        String fx = derivadaFx.replaceAll("\\s+", "");
        String gx = derivadaGx.replaceAll("\\s+", "");

        List<String> terminosFx = new ArrayList<>();
        Matcher m1 = Pattern.compile("[+-]?[^+-]+").matcher(fx);
        while (m1.find()) terminosFx.add(m1.group());

        List<String> terminosGx = new ArrayList<>();
        Matcher m2 = Pattern.compile("[+-]?[^+-]+").matcher(gx);
        while (m2.find()) terminosGx.add(m2.group());

        Map<String, Double> mapa = new HashMap<>();

        // Sumar coeficientes de fx
        for (String termino : terminosFx) {
            Matcher match = Pattern.compile("([+-]?\\d*\\.?\\d*)([a-zA-Z][\\^\\d]*)?").matcher(termino);
            if (match.matches()) {
                String coefStr = match.group(1);
                String var = match.group(2) == null ? "" : match.group(2);
                double coef = (coefStr.equals("+") || coefStr.isEmpty()) ? 1 :
                              (coefStr.equals("-") ? -1 : Double.parseDouble(coefStr));
                mapa.put(var, mapa.getOrDefault(var, 0.0) + coef);
            }
        }

        // Restar coeficientes de gx
        for (String termino : terminosGx) {
            Matcher match = Pattern.compile("([+-]?\\d*\\.?\\d*)([a-zA-Z][\\^\\d]*)?").matcher(termino);
            if (match.matches()) {
                String coefStr = match.group(1);
                String var = match.group(2) == null ? "" : match.group(2);
                double coef = (coefStr.equals("+") || coefStr.isEmpty()) ? 1 :
                              (coefStr.equals("-") ? -1 : Double.parseDouble(coefStr));
                mapa.put(var, mapa.getOrDefault(var, 0.0) - coef);
            }
        }

        return construirResultadoOrdenado(mapa);
    }
    
    // Metodo para la regla de producto entre dos funciones
    // f(x)*g'(x)+g(x)*f'(x)
    public static String reglaPro(String funcionfx, String derivadaGx, String funciongx, String derivadaFx){
        String f1g = multiplicarFunciones(derivadaFx, funciongx); // f'(x) * g(x)
        String fg1 = multiplicarFunciones(funcionfx, derivadaGx); // f(x) * g'(x)

        // Ahora sumamos los dos resultados obtenidos
        return reglaSum(f1g, fg1);
    }
    
    // Metodo para la regla de cociente entre dos funciones
    // (g(x)*f'(x)-f(x)*g'(x))/(g(x))^2 siempre y cuando g(x) != 0
    public static String reglaCo(String funcionfx, String derivadaGx, String funciongx, String derivadafx){
        String f1g = multiplicarFunciones(derivadafx, funciongx); // f'(x) * g(x)
        String fg1 = multiplicarFunciones(funcionfx, derivadaGx); // f(x) * g'(x)

        // Diferencia en el numerador
        String numerador = reglaDif(f1g, fg1);

        // Denominador es g(x)^2
        String denominador = resolverDenominador(funciongx);
        System.out.println("Y nos devuelve: " + denominador);
        
        return numerador + " / " + denominador;
    }
    
    // Metodo para evaluar regla de la constante
    public static String reglaDeConstante( String funcion ){
        
        // Volvemos a eliminar espacios en caso de tenerlos en la función
        funcion = funcion.replace( " ", "" );
        String constante = encontrarConstantes( funcion );
        
        // Limpiamos espacios de la constante en caso de tenerlos
        constante = constante.replace( " ", "" );
        String funcionSinConstante = funcion.replace( constante, "" );
        
        return funcionSinConstante;
    }
    
    // Metodo para evaluar la regla de potencia
    public static String reglaDePotencia(String funcion) {

        funcion = funcion.replace(" ", ""); // Limpieza

        if (!funcion.contains("x")) return ""; // No es una potencia

        // Obtener coeficiente (antes de 'x')
        int xIndex = funcion.indexOf("x");
        String coefStr = funcion.substring(0, xIndex);

        // Verificar si el coeficiente está vacío o es solo un signo
        if (coefStr.isEmpty()) {
            coefStr = "1";  // Si no hay coeficiente, es 1
        } else if (coefStr.equals("+")) {
            coefStr = "1";  // Si el coeficiente es '+', es 1
        } else if (coefStr.equals("-")) {
            coefStr = "-1";  // Si el coeficiente es '-', es -1
        }

        double coef = Double.parseDouble(coefStr);

        // Obtener exponente (después de '^')
        int exp = 1;
        if (funcion.contains("^")) {
            int powIndex = funcion.indexOf("^");
            String expStr = funcion.substring(powIndex + 1);
            exp = Integer.parseInt(expStr);
        }

        double nuevoCoef = coef * exp;
        int nuevoExp = exp - 1;

        // Formatear resultado
        if (nuevoExp == 0) {
            return String.valueOf(nuevoCoef);
        } else if (nuevoExp == 1) {
            return formatearCoef(nuevoCoef) + "x";
        } else {
            return formatearCoef(nuevoCoef) + "x^" + nuevoExp;
        }
    }
    
    //public static String
    /* To do:
    ---- Funciones en fraccion
    ---- Regla de la cadena
    ---- Funciones con radicales
    ---- Funciones trigonometricas
    */
}