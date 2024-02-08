package todolist.Model;

public class Task { // Task significa Tarefa
    // Atributos
    String description;
    boolean done; // done = Finalizado
int getId;
    // construtor
    public Task(String description, boolean done, int getId) {//Construtor é um metodo que contem o mesmo nome da minha classe, serve para a construção. //Para metro são aqueles que estão entre parenteses
        
        this.description = description;
        this.done = false;
        this.getId = 0;
    }

    // Gerar os getters (Buscar a informação) e setters (Alterar a informação), são
    // metodos/funções especiais
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() { // isDone, pois a variavel esta em boolean ela so pode ser dois valores. Porem
                              // se eu preferir mudar o nome para getDone, não há problma nenhum
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getGetId() {
        return getId;
    }

    public void setGetId(int getId) {
        this.getId = getId;
    }

}
