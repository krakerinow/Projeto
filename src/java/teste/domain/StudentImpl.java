package teste.domain;

public class StudentImpl extends Student {
    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                '}';
    }
}
