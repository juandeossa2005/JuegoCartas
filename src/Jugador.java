import java.util.Random;

import javax.swing.JPanel;

public class Jugador {

    private int DISTANCIA = 40;
    private int MARGEN = 10;
    private int TOTAL_CARTAS = 10;
    private String MENSAJE_PREDETERMINADO = "No se encontraron grupos";
    private String ENCABEZADO_MENSAJE = "Se encontraron los siguientes grupos:\n",ENCABEZADO_MENSAJE_PINTA = "Se encontraron los siguientes grupos de Pinta:\n";
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
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
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

        return mensaje;
    }
    public String getPinta() {
        String mensaje = ENCABEZADO_MENSAJE_PINTA;
        // Matriz para contar cartas por pinta y numero
        int[][] contadoresPinta = new int[Pinta.values().length][NombreCarta.values().length];
        // Llenar la matriz con la cantidad de cada carta
        for (Carta carta : cartas) {
            contadoresPinta[carta.getPinta().ordinal()][carta.getNombre().ordinal()]++;
        }
        // Buscar secuencias consecutivas dentro de cada pinta
        boolean hayPinta = false;
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
                        hayPinta = true;
                        mensaje += Grupo.values()[contadorConsecutivo] + " de " + Pinta.values()[i] + ": " + grupoCartas.substring(0, grupoCartas.length() - 2) + "\n";
                    }
                    contadorConsecutivo = 0;
                    grupoCartas = "";

                }
            }
            // Verificar si el último grupo de la pinta fue válido
            if (contadorConsecutivo >= MNIMA_CANTIDAD_GRUPO) {
                hayPinta = true;
                mensaje += "Grupo de " + Pinta.values()[i] + ": " + grupoCartas.substring(0, grupoCartas.length() - 2) + "\n";
            }
        }
        return hayPinta ? mensaje : MENSAJE_PREDETERMINADO;
    }
}
