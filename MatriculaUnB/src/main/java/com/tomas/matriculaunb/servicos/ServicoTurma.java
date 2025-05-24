package com.tomas.matriculaunb.servicos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tomas.matriculaunb.modelo.*;

import java.util.List;
import java.util.UUID;

public class ServicoTurma extends ClasseServicoBase{

    final String nomeArquivo="turma.txt";

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
        Disciplina disciplinaRetornada;
        disciplinaRetornada = (Disciplina) this.getLista().stream()
                .filter(obj -> ((Disciplina)obj).getTitulo().equals(titulo))
                .findFirst()
                .orElse(null);
        if (disciplinaRetornada!=null){
            return (Disciplina) disciplinaRetornada.clone();
        }
        return null;
    }

    public List<ClasseBase> getTurmasPorDisciplina(Disciplina disciplina){

        return this.getLista().stream()
                .filter(turma->((Turma)turma).getDisciplina().equals(disciplina))
                .toList();
    }

    public List<ClasseBase> getTurmasPorProfessor(Professor professor){

        return this.getLista().stream()
                .filter(turma->((Turma)turma).getProfessor().equals(professor))
                .toList();
    }

    public List<ClasseBase> getTurmasPorSala(Sala sala){

        return this.getLista().stream()
                .filter(turma->((Turma)turma).getSala().equals(sala))
                .toList();
    }

    //verifica duplicacao de disciplina,horario e semestre
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

    //verifica duplicacao de professor, horario e semestre
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

    //verifica duplicacao de sala, horario e semestre
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
    public boolean existeSala(UUID idSala){
        if (this.getLista()==null){
            return false;
        }
        return this.getLista().stream()
                .anyMatch( obj->((Turma)obj).getSala().getId().equals(idSala));
    }
    public void salvarArquivo() throws Exception{
        this.salvarListaParaArquivo(nomeArquivo);
    }
    public void carregarArquivo() throws Exception{
        this.lerArquivoParaLista(nomeArquivo,new TypeReference<List<Turma>>() {});
    }

    public void salvarArquivo(Turma turmaAlterada) throws Exception{
        this.salvarListaParaArquivo(nomeArquivo);

        //alterar as turmas da lista de alunosMatriculados
        ServicoAlunoMatriculado servicoAlunoMatriculado=ServicoAlunoMatriculado.getInstance();
        List<ClasseBase> listaAlunosMatriculados = servicoAlunoMatriculado.getAlunosMatriculadosPorTurma(turmaAlterada);
        for(ClasseBase alunoMatriculado:listaAlunosMatriculados){
            ((AlunoMatriculado)alunoMatriculado).setTurma(turmaAlterada);
        }
        servicoAlunoMatriculado.salvarArquivo();

    }
}
