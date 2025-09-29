package main.java.org.jcr.entidades;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Data
@EqualsAndHashCode(exclude = {"citas"})
@ToString(exclude = {"citas"})
public class Medico extends Persona implements Serializable {

    private final Matricula matricula;
    private final EspecialidadMedica especialidad;

    @Setter
    private Departamento departamento;

    private final List<Cita> citas = new ArrayList<>();

    public Medico(String nombre, String apellido, String dni, LocalDate fechaNacimiento,
                  TipoSangre tipoSangre, String numeroMatricula, EspecialidadMedica especialidad) {
        super(nombre, apellido, dni, fechaNacimiento, tipoSangre);
        this.matricula = new Matricula(numeroMatricula);
        this.especialidad = Objects.requireNonNull(especialidad, "La especialidad no puede ser nula");
    }

    public void addCita(Cita cita) {
        this.citas.add(cita);
    }

    public List<Cita> getCitas() {
        return Collections.unmodifiableList(new ArrayList<>(citas));
    }

    @Override
    public String toString() {
        return String.format("Medico{nombre='%s', apellido='%s', especialidad=%s, matricula=%s}",
                nombre,
                apellido,
                especialidad.getDescripcion(),
                matricula.getNumero());
    }
}
