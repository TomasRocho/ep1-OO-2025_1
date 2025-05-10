package com.tomas.matriculaunb.servicos;

import com.tomas.matriculaunb.modelo.Aluno;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Disciplina;
import com.tomas.matriculaunb.modelo.Turma;

import java.util.UUID;

public class ServicoTurma extends ClasseServicoBase{

    //CLASSE SINGLETON
    private static ServicoTurma instance = null;
    private ServicoTurma(){}
    public static ServicoTurma getInstance() {
        if (instance == null) {
            instance = new ServicoTurma();
        }
        return instance;
    }

    @Override
    public boolean podeIncluir(ClasseBase turma) throws Exception{

        turma.validar();
        if (this.existe(turma.getId())){
            throw new Exception("Impossível Incluir Turma - id duplicado");
        }
        if (disciplinaDuplicada((Turma) turma,false)){
            throw new Exception("Impossível Incluir Turma - disciplina,horario e semestre duplicados");
        }
        if (professorDuplicado((Turma) turma,false)){
            throw new Exception("Impossível Incluir Turma - professor,horario e semestre duplicados");
        }
        if (salaDuplicada((Turma) turma,false)){
            throw new Exception("Impossível Incluir Turma - sala,horario e semestre duplicados");
        }
        return true;
    }

    @Override
    public boolean podeAlterar(ClasseBase turma) throws Exception{

        turma.validar();
        if (disciplinaDuplicada((Turma) turma,true)){
            throw new Exception("Impossível Alterar Turma - disciplina,horario e semestre duplicados");
        }
        if (professorDuplicado((Turma) turma,true)){
            throw new Exception("Impossível Alterar Turma - professor,horario e semestre duplicados");
        }
        if (salaDuplicada((Turma) turma,true)){
            throw new Exception("Impossível Alterar Turma - sala,horario e semestre duplicados");
        }
        return true;
    }

    @Override
    public boolean podeExcluir(ClasseBase turma) throws Exception{

        ServicoAlunoMatriculado servicoAlunoMatriculado = ServicoAlunoMatriculado.getInstance();
        if (servicoAlunoMatriculado.existeTurma(turma.getId())){
            throw new Exception("Impossível Excluir Turma - Alunos matriculados nesta turma");
        }

        return true;
    }

    public Disciplina retornarPorTitulo(String titulo) throws Exception{

        if (this.getLista()==null){
            return null;
        }
        Disciplina disciplinaRetornada = null;
        disciplinaRetornada = (Disciplina) this.getLista().stream()
                .filter(obj -> ((Disciplina)obj).getTitulo().equals(titulo))
                .findFirst()
                .orElse(null);
        if (disciplinaRetornada!=null){
            return (Disciplina) disciplinaRetornada.clone();
        }
        return null;
    }

    public boolean disciplinaDuplicada(Turma turma, boolean alteracao){
        if (this.getLista()==null){
            return false;
        }
        if (!alteracao){
            return this.getLista().stream()
                    .anyMatch( obj->((Turma)obj).getDisciplina().equals(turma.getDisciplina())
                                                && ((Turma)obj).getHorario().equals(turma.getHorario())
                                                && ((Turma)obj).getSemestreAno().equals(turma.getSemestreAno()));
        }
        return this.getLista().stream()
                .anyMatch( obj->(((Turma)obj).getDisciplina().equals(turma.getDisciplina())
                        && ((Turma)obj).getHorario().equals(turma.getHorario())
                        && ((Turma)obj).getSemestreAno().equals(turma.getSemestreAno())
                        && !obj.getId().equals(turma.getId())));
    }

    public boolean professorDuplicado(Turma turma, boolean alteracao){
        if (this.getLista()==null){
            return false;
        }
        if (!alteracao){
            return this.getLista().stream()
                    .anyMatch( obj->((Turma)obj).getProfessor().equals(turma.getProfessor())
                            && ((Turma)obj).getHorario().equals(turma.getHorario())
                            && ((Turma)obj).getSemestreAno().equals(turma.getSemestreAno()));
        }
        return this.getLista().stream()
                .anyMatch( obj->(((Turma)obj).getProfessor().equals(turma.getProfessor())
                        && ((Turma)obj).getHorario().equals(turma.getHorario())
                        && ((Turma)obj).getSemestreAno().equals(turma.getSemestreAno())
                        && !obj.getId().equals(turma.getId())));
    }

    public boolean salaDuplicada(Turma turma, boolean alteracao){
        if (this.getLista()==null){
            return false;
        }
        if (!alteracao){
            return this.getLista().stream()
                    .anyMatch( obj->((Turma)obj).getSala().equals(turma.getSala())
                            && ((Turma)obj).getHorario().equals(turma.getHorario())
                            && ((Turma)obj).getSemestreAno().equals(turma.getSemestreAno()));
        }
        return this.getLista().stream()
                .anyMatch( obj->(((Turma)obj).getSala().equals(turma.getSala())
                        && ((Turma)obj).getHorario().equals(turma.getHorario())
                        && ((Turma)obj).getSemestreAno().equals(turma.getSemestreAno())
                        && !obj.getId().equals(turma.getId())));
    }
    public boolean existeDisciplina(UUID idDisciplina){
        if (this.getLista()==null){
            return false;
        }
        return this.getLista().stream()
                .anyMatch( obj->((Turma)obj).getDisciplina().getId().equals(idDisciplina));
    }
    public boolean existeProfessor(UUID idProfessor){
        if (this.getLista()==null){
            return false;
        }
        return this.getLista().stream()
                .anyMatch( obj->((Turma)obj).getProfessor().getId().equals(idProfessor));
    }

}
