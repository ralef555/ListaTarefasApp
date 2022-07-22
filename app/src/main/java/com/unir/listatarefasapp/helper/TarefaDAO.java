package com.unir.listatarefasapp.helper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.unir.listatarefasapp.model.Tarefa;

import java.util.ArrayList;

public class TarefaDAO implements ITarefaDAO {

    private SQLiteDatabase escrita;
    private SQLiteDatabase leitura;


    public TarefaDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escrita = db.getWritableDatabase();
        leitura = db.getReadableDatabase();

        @Override
        public boolean salvar (Tarefa tarefa){
            ContentValues cv = new ContentValues();
            cv.put("nome", tarefa.getNomeTarefa());
        }try{
            escrita.insert(DbHelper.TABELA_TAREFAS, null, cv);
            Log.i("INFO", "Tarefa salva com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao salvar a tarefa!" + e.getMessage());
            return false;
        }
        return true;

    }

    @Override
    public boolean atualizar (Tarefa tarefa){//LISTA DE CRITERIOS PARA ATUALIZAÇÃO
        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        try{
            String [] args = {String.valueOf(tarefa.getId())};
            escrita.update(DbHelper.TABELA_TAREFAS, cv, "id=?", args);
            Log.i("INFO", "Tarefa atualizada com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizar a tarefa!" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar (Tarefa tarefa){
        try{
            String [] args = {String.valueOf(tarefa.getId())};
            escrita.delete(DbHelper.TABELA_TAREFAS, "id=?", args);
            Log.i("INFO", "Tarefa removida com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao remover a tarefa!" + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Tarefa> listar(){
        List<Tarefa> tarefas = new ArrayList<Tarefa>();
        String sql = "SELECT * FROM " + DbHelper.TABELA_TAREFAS + " ;";
        Cursos c = leitura.rawQuery(sql, null);
        while (c.moveToNext()){
            Tarefa tarefa = new Tarefa();
            @SuppressLint("Range") Long id = c.getLong(c.getColumnIndex("id"));
            @SuppressLint ("Range") String nomeTarefa = c.getString(c.getColumnIndex("nome"));
            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);
            tarefas.add(tarefa);
        }
        return tarefas;
    }

}
