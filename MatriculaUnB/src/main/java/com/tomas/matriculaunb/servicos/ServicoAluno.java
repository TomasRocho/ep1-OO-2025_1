package com.tomas.matriculaunb.servicos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tomas.matriculaunb.modelo.Aluno;
import com.tomas.matriculaunb.modelo.AlunoMatriculado;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Curso;
import com.tomas.matriculaunb.modelo.enumerations.EnumStatusAlunoMatriculado;

import java.util.List;
import java.util.UUID;

public class ServicoAluno extends ClasseServicoBase{

    public final String nomeArquivo="aluno.txt";

    //CLASSE SINGLETON
    private static ServicoAluno instance = null;
    private ServicoAluno(){}
    public static ServicoAluno getInstance() {
        if (instance == null) {
            instance = new ServicoAluno();

        }
        return instance;
    }


    @Override
    public boolean podeIncluir(ClasseBase aluno) throws Exception{

       aluno.validar();

        if (this.existe(aluno.getId())){
            throw new Exception("Impossível Incluir Aluno - id duplicado");
        }
        if (matriculaDuplicada((Aluno) aluno,false)){
            throw new Exception("Impossível Incluir Aluno - matricula duplicada");
        }

        return true;
    }

    @Override
    public boolean podeAlterar(ClasseBase alunoAlterado) throws Exception{

        alunoAlterado.validar();
        if (matriculaDuplicada((Aluno) alunoAlterado,true)){
            throw new Exception("Impossível Alterar Aluno - matricula duplicada");
        }
        return true;
    }

    @Override
    public boolean podeExcluir(ClasseBase alunoAlterado) throws Exception{
        ServicoAlunoMatriculado servicoAlunoMatriculado = ServicoAlunoMatriculado.getInstance();
        if (servicoAlunoMatriculado.existeAluno(alunoAlterado.getId())){
            throw new Exception("Impossível Excluir Aluno - aluno matriculado em alguma turma");
        }

        return true;
    }

    public Aluno retornarPorNome(String nome) throws Exception{

        if (this.getLista()==null){
            return null;
        }
        Aluno alunoRetornado ;
        alunoRetornado = (Aluno)this.getLista().stream()
                .filter(obj -> ((Aluno)obj).getNome().equals(nome))
                .findFirst()
                .orElse(null);
        if (alunoRetornado!=null){
            return (Aluno) alunoRetornado.clone();
        }
        return null;
    }
    public Aluno retornarPorMatricula(String matricula) throws Exception{

        if (this.getLista()==null){
            return null;
        }
        Aluno alunoRetornado;
        alunoRetornado = (Aluno)this.getLista().stream()
                .filter(obj -> ((Aluno)obj).getMatricula().equals(matricula))
                .findFirst()
                .orElse(null);
        if (alunoRetornado!=null){
            return (Aluno) alunoRetornado.clone();
        }
        return null;
    }

    public boolean matriculaDuplicada(Aluno aluno, boolean alteracao){
        if (this.getLista()==null){
            return false;
        }
        if (!alteracao){
            return this.getLista().stream()
                    .anyMatch( obj->((Aluno)obj).getMatricula().equals(aluno.getMatricula()));
        }
        return this.getLista().stream()
                .anyMatch( obj->((Aluno)obj).getMatricula().equals(aluno.getMatricula())
                        && !obj.getId().equals(aluno.getId()));
    }

    public void trancarSemestre(Aluno aluno, String semestreAno){
        ServicoAlunoMatriculado servicoAlunoMatriculado = ServicoAlunoMatriculado.getInstance();
        List<AlunoMatriculado> listaMatriculasSemestre = servicoAlunoMatriculado.getListaMatriculasPorAluno(aluno.getId(),semestreAno);
        for (AlunoMatriculado matricula:listaMatriculasSemestre){
            if (matricula.retornarStatus().equals(EnumStatusAlunoMatriculado.EmCurso)){
                matricula.setTrancado(true);
            }
        }
    }

    public List<ClasseBase> getAlunosPorCurso(Curso curso){

        return this.getLista().stream()
                .filter(aluno->((Aluno)aluno).getCurso().equals(curso))
                .toList();
    }

    public boolean existeCurso(UUID idCurso){
        if (this.getLista()==null){
            return false;
        }
        return this.getLista().stream()
                .anyMatch(aluno->((Aluno)aluno).getCurso().getId().equals(idCurso));
    }
    public void salvarArquivo() throws Exception{
        this.salvarListaParaArquivo(nomeArquivo);
    }
    public void carregarArquivo() throws Exception{
        this.lerArquivoParaLista(nomeArquivo,new TypeReference<List<Aluno>>() {});
    }

    public void salvarArquivo(Aluno alunoAlterado) throws Exception{
        this.salvarListaParaArquivo(nomeArquivo);


        //alterar os alunos da lista de alunosMatriculados
        ServicoAlunoMatriculado servicoAlunoMatriculado=ServicoAlunoMatriculado.getInstance();
        List<ClasseBase> listaAlunosMatriculados = servicoAlunoMatriculado.getAlunosMatriculadosPorAluno(alunoAlterado);
        for(ClasseBase alunoMatriculado:listaAlunosMatriculados){
            ((AlunoMatriculado)alunoMatriculado).setAluno(alunoAlterado);
        }
        servicoAlunoMatriculado.salvarArquivo();

    }
}
