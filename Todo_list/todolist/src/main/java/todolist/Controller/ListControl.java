package todolist.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import todolist.Controller.Connection.ListDAO;
import todolist.Model.Task;

public class ListControl {

    // AQUI É ONDE SE AFUNILA AS INFOMAÇÕES

    // Atributos
    private List<Task> tasks;
    private DefaultTableModel tableModel;
    private DefaultListModel<String> listModel;

    private JTable table;

    // Construtor
    public ListControl(List<Task> tasks, DefaultTableModel tableModel, JTable table) {
        this.tasks = tasks;
        this.tableModel = tableModel;
        this.table = table;

    }

    // Método para atualizar a tabela de exibição com dados do banco de dados
    public void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        tasks = new ListDAO().listarTodos();
        // Obtém os carros atualizados do banco de dados
        for (Task tarefa : tasks) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { tarefa.getDescription(), tarefa.isDone(), tarefa.getGetId()});
        }
    }

    // Método para cadastrar um novo carro no banco de dados
    public void cadastrar(String tarefa, Boolean concluida) {
        new ListDAO().cadastrar(tarefa, concluida);
        // Chama o método de cadastro no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após o cadastro

    }

    // Método para atualizar os dados de um carro no banco de dados
    public void atualizar(String tarefa, Boolean concluida, int id) {
        new ListDAO().atualizar(tarefa, concluida, id);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

    // Método para apagar um carro do banco de dados
    public void apagar(String id) { // Placa é a minha chave primária
        new ListDAO().apagar(id);
        // Chama o método de exclusão no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
    }

   /*  // CREATE, C DO CRUD
    private void addTask(JTextField inputTextField) {
        // Adiciona uma nova task à lista de tasks
        int funciona; // Variavel criada para receber valor do JOptionPane
        funciona = JOptionPane.showConfirmDialog(null, "Deseja realmente adicionar tarefa:");
        // Para funcionar tanto no apertar do botão quanto para ao pressionar a tecla
        // ENTER
        if (funciona == JOptionPane.YES_OPTION) { // Se a escolha for SIM
            String taskDescription = inputTextField.getText().trim();// TRIM = remove espaços vazios
            if (!taskDescription.isEmpty()) { // Se estiver diferente de vazio
                Task newTask = new Task(taskDescription, false,);
                tasks.add(newTask); // Adicionando novas tarefas ao Array
                updateTaskList();// Chama o outro metodo
                inputTextField.setText("");
            }
        } else if (funciona == JOptionPane.NO_OPTION) { // Se a escolha for NÃO
            inputTextField.setText("");
        } // Se a escolha for CANCEL o JOption ira apenas fechar.
    }
 */
    // DELETE, D DO CRUD
    private void deleteTask(JList<String> taskList) {
        // Exclui a task selecionada da lista de tasks
        int selectedIndex = taskList.getSelectedIndex();
        try {

        } catch (Exception e) {
            // TODO: handle exception
        } // Pega o index da tarefa que esta selecionado
        if (selectedIndex >= 0 && selectedIndex < tasks.size()) { // Vê se ela existe
            tasks.remove(selectedIndex); // tasks é o meu Arraylist
            updateTaskList(); // Atualiza o Scroll
        }
    }

    // UPDATE, U do CRUD
    public void markTaskDone(JList<String> taskList) {
        // Marca a task selecionada como concluída
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < tasks.size()) {
            Task task = tasks.get(selectedIndex); // Pegou o elemento do arraylist
            task.setDone(true); // Usando o Setters do outro metodo
            updateTaskList(); // Atualiza a tasklist
        }
    }

    // READ, R do CRUD
    private void filterTasks(JComboBox combobox) {
        // Filtra as tasks com base na seleção do JComboBox
        String filter = (String) combobox.getSelectedItem();
        listModel.clear();
        // FOREACH percorre o meu Arraylist
        for (Task task : tasks) {
            if (filter.equals("Todas") || (filter.equals("Ativas") &&
                    !task.isDone()) || (filter.equals("Concluídas") && task.isDone())) {
                listModel.addElement(task.getDescription());
            }
        }
    }

    private void clearCompletedTasks() {
        // Limpa todas as tasks concluídas da lista
        List<Task> completedTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isDone()) {
                completedTasks.add(task);
            }
        }
        tasks.removeAll(completedTasks);
        updateTaskList();
    }

    private void updateTaskList() {
        // Atualiza a lista de tasks exibida na GUI
        listModel.clear();
        for (Task task : tasks) {
            if (task.isDone()) {
                listModel
                        .addElement("<html><font color='green'>" + task.getDescription() + "(Concluida)</font></html>");
            } else {
                listModel.addElement(task.getDescription());
            }
            // tableModel é a lista simplificada chamado ternário

        }
    }

}
