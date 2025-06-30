package Modelo;

public enum Especialidad {
    
        MEDICINAGENERAL("Medicina General"),
        CARDIOLOGIA("Cardiología"),
        NEUMOLOGIA("Neumología"),
        GASTROENTEROLOGIA("Gastroenterología"),
        TRAUMATOLOGIA("Traumatología"),
        OTORRINOLARINGOLOGIA("Otorrinolaringología");
        
        private final String descripcionEspecialidad;

        private Especialidad(String descripcionEspecialidad) {
            this.descripcionEspecialidad = descripcionEspecialidad;
        }
        
        public String getDescripcion() {
            return descripcionEspecialidad;
        }

        @Override
        public String toString() {
            return descripcionEspecialidad;
        }
}
