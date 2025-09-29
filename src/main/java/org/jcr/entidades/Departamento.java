package main.java.org.jcr.entidades;

import lombok.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Departamento implements Serializable {

    @NonNull
    private final String nombre;

    @NonNull
    private final EspecialidadMedica especialidad;

    @Setter
    @EqualsAndHashCode.Exclude
    private Hospital hospital;

    @EqualsAndHashCode.Exclude
    private final List<Medico> medicos = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    private final List<Sala> salas = new ArrayList<>();

    public void setHospital(Hospital hospital) {
        if (this.hospital != hospital) {
            if (this.hospital != null) {
                this.hospital.getInternalDepartamentos().remove(this);
            }
            this.hospital = hospital;
            if (hospital != null) {
                hospital.getInternalDepartamentos().add(this);
            }
        }
    }

    public void agregarMedico(Medico medico) {
        if (medico != null && !medicos.contains(medico)) {
            medicos.add(medico);
            medico.setDepartamento(this);
        }
    }

    public Sala crearSala(String numero, String tipo) {
        Sala sala = new Sala(numero, tipo, this);
        salas.add(sala);
        return sala;
    }

    public List<Medico> getMedicos() {
        return Collections.unmodifiableList(medicos);
    }

    public List<Sala> getSalas() {
        return Collections.unmodifiableList(salas);
    }

    @Override
    public String toString() {
        return String.format("Departamento{nombre='%s', especialidad=%s, hospital=%s}",
                nombre,
                especialidad.getDescripcion(),
                hospital != null ? hospital.getNombre() : "null");
    }
}
