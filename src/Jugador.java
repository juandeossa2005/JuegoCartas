import java.util.Random;

import javax.swing.JPanel;

public class Jugador {

    private int DISTANCIA = 40;
    private int MARGEN = 10;
    private int TOTAL_CARTAS = 10;
    private String MENSAJE_PREDETERMINADO = "No se encontraron grupos";
    private String ENCABEZADO_MENSAJE = "Se encontraron los siguientes grupos:\n";
    private int MNIMA_CANTIDAD_GRUPO = 2;
    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int x = MARGEN + (TOTAL_CARTAS - 1) * DISTANCIA;
        for (Carta carta : cartas) {
            carta.mostrar(pnl, x, MARGEN);
            x -= DISTANCIA;
        }

        pnl.repaint();
    }

    public String getGrupos() {
        String mensaje = MENSAJE_PREDETERMINADO;

        int[] contadores = new int[NombreCarta.values().length];
        int[][] contadoresPinta = new int[Pinta.values().length][NombreCarta.values().length];

        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
            contadoresPinta[carta.getPinta().ordinal()][carta.getNombre().ordinal()]++;
        }

        boolean hayGrupos = false;
        for (int contador : contadores) {
            if (contador >= MNIMA_CANTIDAD_GRUPO) {
                hayGrupos = true;
                break;
            }
        }

        if (hayGrupos) {
            mensaje = ENCABEZADO_MENSAJE;
            int posicion = 0;
            for (int contador : contadores) {
                if (contador >= MNIMA_CANTIDAD_GRUPO) {
                    mensaje += Grupo.values()[contador] + " de " + NombreCarta.values()[posicion] + "\n";
                }
                posicion++;
            }
        }
        for (int i = 0; i < Pinta.values().length; i++) { // Recorre cada pinta
            int contadorConsecutivo = 0;
            String grupoCartas = "";
            for (int j = 0; j < NombreCarta.values().length; j++) { // Recorre cada valor de carta en la pinta
                if (contadoresPinta[i][j] > 0) { // Si existe al menos una carta de este valor
                    contadorConsecutivo++;
                    grupoCartas += NombreCarta.values()[j] + ", ";
                } else {
                    // Si se rompe la secuencia, verificar si es un grupo válido
                    if (contadorConsecutivo >= MNIMA_CANTIDAD_GRUPO) {
                        hayGrupos = true;
                        mensaje += Grupo.values()[contadorConsecutivo] + " de " + Pinta.values()[i] + ": " + grupoCartas.substring(0, grupoCartas.length() - 2) + "\n";
                    }
                    contadorConsecutivo = 0;
                    grupoCartas = "";
                }
            }
            // Verificar si el último grupo de la pinta fue válido
            if (contadorConsecutivo >= MNIMA_CANTIDAD_GRUPO) {
                hayGrupos = true;
                mensaje += Grupo.values()[contadorConsecutivo] + " de " + Pinta.values()[i] + ": " + grupoCartas.substring(0, grupoCartas.length() - 2) + "\n";
            }
        }
        return mensaje;
    }
    public String getPinta() {
        String mensaje = MENSAJE_PREDETERMINADO;
        int[] contadores = new int[NombreCarta.values().length];
        int[][] contadoresPinta = new int[Pinta.values().length][NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
            contadoresPinta[carta.getPinta().ordinal()][carta.getNombre().ordinal()]++;
        }
        boolean hayGrupos = false;
        for (int contador : contadores) {
            if (contador >= MNIMA_CANTIDAD_GRUPO) {
                hayGrupos = true;
                break;
            }
        }
        if (hayGrupos) {
            mensaje = ENCABEZADO_MENSAJE;
            int posicion = 0;
            for (int contador : contadores) {
                if (contador >= MNIMA_CANTIDAD_GRUPO) {
                    mensaje += Grupo.values()[contador] + " de " + NombreCarta.values()[posicion] + "\n";
                }
                posicion++;
            }
        }
        int puntos = 0;
        for (int i = 0; i < Pinta.values().length; i++) { // Recorre cada pinta
            int contadorConsecutivo = 0;
            String grupoCartas = "";
            for (int j = 0; j < NombreCarta.values().length; j++) { // Recorre cada valor de carta en la pinta
                if (contadoresPinta[i][j] > 0) { // Si existe al menos una carta de este valor
                    contadorConsecutivo++;
                    grupoCartas += NombreCarta.values()[j] + ", ";
                } else {
                    // Si se rompe la secuencia, verificar si es un grupo válido
                    if (contadorConsecutivo >= MNIMA_CANTIDAD_GRUPO) {
                        hayGrupos = true;
                        mensaje += Grupo.values()[contadorConsecutivo] + " de " + Pinta.values()[i] + ": " + grupoCartas.substring(0, grupoCartas.length() - 2) + "\n";
                    }else{
                        if (j > 0 && contadoresPinta[i][j-1] == 1) {
                            int suma = 0;
                            for (int f = 0; f < Pinta.values().length; f++) { // Recorre las filas
                                suma += contadoresPinta[f][j-1]; // Suma el valor en la columna específica
                            }
                            if (suma == 1) {
                                if (j >= 10) {
                                    puntos += 10;
                                } else {
                                    puntos += j;
                                }
                            }
                        }
                    }
                    contadorConsecutivo = 0;
                    grupoCartas = "";
                }
            }
            // Verificar si el último grupo de la pinta fue válido ya que al no tener otra carta que validar se podria perder el grupo
            if (contadorConsecutivo >= MNIMA_CANTIDAD_GRUPO) {
                hayGrupos = true;
                mensaje += Grupo.values()[contadorConsecutivo] + " de " + Pinta.values()[i] + ": " + grupoCartas.substring(0, grupoCartas.length() - 2) + "\n";
            }else{
                int j = 13;
                if (j > 0 && contadoresPinta[i][j-1] == 1) {
                    int suma = 0;
                    for (int f = 0; f < Pinta.values().length; f++) { // Recorre las filas
                        suma += contadoresPinta[f][j-1]; // Suma el valor en la columna específica
                    }
                    if (suma == 1) {
                        if (j >= 10) {
                            puntos += 10;
                        } else {
                            puntos += j;
                        }
                    }
                }
            }
        }
        mensaje += "Total de puntos"+ puntos + "\n";
        return mensaje;
    }
    public int sumarColumna(int[][] matriz, int col) {
        int suma = 0;
        for (int i = 0; i < matriz.length; i++) { // Recorre las filas
            suma += matriz[i][col]; // Suma el valor en la columna específica
        }
        return suma;
    }
}
