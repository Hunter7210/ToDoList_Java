package todolist.View;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import todolist.Controller.ListControl;
import todolist.Controller.Connection.ListDAO;
import todolist.Model.Task;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class PainelVisual extends JFrame {
    // ATRIBUTOS
    private JPanel mainPanel;
    private JTextField taskInputField, taskInputField2;
    private JButton addButton;

    // Componenetes novos
    // Ambas são da classe swing, ou seja, é focado para criação de tabelas.
    // Basicamente ela pega as listas que existem no meu sistema e a colocam de uma
    // forma grafica

    private JList<String> taskList; // JList é uma lista grafica, no caso pega so elementos da classe Task lá em
                                    // baixo
    private DefaultListModel<String> listModel;
    // Atributos
    private DefaultTableModel tableModel;

    private JTable table;

    private JButton deleteButton;
    private JButton markDoneButton;
    private JComboBox<String> filterComboBox;
    private JButton clearCompletedButton;

    private List<Task> tasks;// Interface de Arraylist, não é possível instanciar deve ser usado o Arraylist

    // Construtor
    public PainelVisual() {
        // Configuração da janela principal
        super("To-Do List App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(500, 100, 450, 400);

        // Inicializa o painel principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Inicializa a lista de tasks e a lista de tasks concluídas
        tasks = new ArrayList<>(); // Task é o nome da minha list, não é possível instanciar objeots da classe list
        listModel = new DefaultTableModel<>(); // Seta os modelos
        taskList = new JList<>(listModel);

        // Inicializa campos de entrada, botões e JComboBox
        taskInputField = new JTextField();
        taskInputField2 = new JTextField();
        addButton = new JButton("Adicionar");
        deleteButton = new JButton("Excluir");
        markDoneButton = new JButton("Concluir");
        filterComboBox = new JComboBox<>(new String[] { "Todas", "Ativas", "Concluídas" });
        clearCompletedButton = new JButton("Limpar Concluídas");

        // Configuração do painel de entrada
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(taskInputField, BorderLayout.CENTER);
        inputPanel.add(taskInputField2, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        // Configuração do painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(deleteButton);
        buttonPanel.add(markDoneButton);
        buttonPanel.add(filterComboBox);
        buttonPanel.add(clearCompletedButton);

        // Adiciona os componentes ao painel principal
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(taskList), BorderLayout.CENTER);// ScrollPane, é baseada na taskList
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Adiciona o painel principal à janela
        this.add(mainPanel);

        ListControl controlList = new ListControl(tasks, tableModel, table);
        new ListDAO().criarTabela();

        atualizarTabela();
        // Eventos

        taskList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // Percorre o Arraylist, pegando os INDEX
                    int index = taskList.getSelectedIndex();
                    if (index >= 0 && index < tasks.size()) {
                        Task task = tasks.get(index); // Pegou o elemento do arraylist
                        task.setDone(true); // Usando o Setters do outro metodo
                        controlList.cadastrar(taskInputField.getText(), taskInputField2.getText());
                    }
                }
            }
        });

        // EVENTO TECLADO
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cadastrando a tarefa
                // Adiciona uma nova task à lista de tasks
                int funciona; // Variavel criada para receber valor do JOptionPane
                funciona = JOptionPane.showConfirmDialog(null, "Deseja realmente adicionar tarefa:");
                // Para funcionar tanto no apertar do botão quanto para ao pressionar a tecla
                // ENTER
                if (funciona == JOptionPane.YES_OPTION) { // Se a escolha for SIM
                    System.out.println("YES");
                    String taskDescription = taskInputField.getText();// TRIM = remove espaços vazios

                    if (!taskDescription.isEmpty()) { // Se estiver diferente de vazio
                        System.out.println("Passou if");
                        Task newTask = new Task(taskDescription, false);
                        tasks.add(newTask); // Adicionando novas tarefas ao Array
                        System.out.println("Add tarefas");
                        controlList.cadastrar(taskDescription, "3");

                        taskInputField.setText("");
                    } else {
                        System.out.println("DEU ERRADO");
                    }
                } else if (funciona == JOptionPane.NO_OPTION) { // Se a escolha for NÃO
                    System.out.println("NO");
                    taskInputField.setText("");
                } // Se a escolha for CANCEL o JOption ira apenas fechar.

            }

        });

       /*  addButton.addKeyListener(new KeyAdapter() {
            // Adionar uma tarefa apertando a tecla ENTER
            public void keyPressed(KeyEvent e) {
                // if para comparar se o que foi digitado é igual a tecla Enter
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { // VK.ENTER é o codigo para a tecla enter
                    // Perguntar se deseja adcionar
                    // Chama o metodo addTask.
                    try {
                        if (!taskInputField.getText().isEmpty()) {

                            // Marca a task selecionada como concluída
                            controlList.cadastrar(taskInputField.getText(), taskInputField2.getText());
                        }

                    } catch (Exception f) {
                        JOptionPane.showMessageDialog(null, "erro");
                        // estiver vazio
                    }
                }

            }

        });
 */
        this.addWindowListener(new WindowAdapter() {
            // Metodo criado para inicio
            @Override
            public void windowOpened(WindowEvent e) {
                // Metodo criado para inicio
                int ativarJFrame = 0;
                setVisible(false);
                ativarJFrame = JOptionPane.showConfirmDialog(null, "SEJA BEM VINDO!\n DESEJA INICIAR?", "BEM VINDO",
                        ativarJFrame);

                if (ativarJFrame == JOptionPane.YES_OPTION) {
                    setVisible(true);
                }
            }
        });
    }

    // Método para atualizar a tabela de exibição com dados do banco de dados
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        tasks = new ListDAO().listarTodos();
        // Obtém os carros atualizados do banco de dados
        for (Task task : tasks) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] {task.getDescription(), task.isDone()});
        }
    }

    public void run() {
        this.setVisible(true);
    }
}
