package com.tomas.matriculaunb.servicos;

import com.tomas.matriculaunb.modelo.Aluno;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Disciplina;
import com.tomas.matriculaunb.modelo.Turma;

import java.util.UUID;

public class ServicoTurma extends ClasseServicoBase{
    //Atributos obrigatorios: id,Disciplina,Proefssor,Sala,Horario,semestreAno
    //nao podem duplicar:
    //id
    //-dispilina,horario,semestreAno
    //-professor,horario,semestreAno
    //-sala,horario,semstreAno;

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

        //todo: verificar se essa turma nao esta sendo por algum AlunoMatriculado

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

}
