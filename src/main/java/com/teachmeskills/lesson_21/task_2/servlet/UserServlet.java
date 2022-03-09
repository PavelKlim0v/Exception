package com.teachmeskills.lesson_21.task_2.servlet;

import com.teachmeskills.lesson_21.task_2.connector.MySQLConnector;
import com.teachmeskills.lesson_21.task_2.crud.CRUD;
import com.teachmeskills.lesson_21.task_2.model.User;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@WebServlet("/")
public class UserServlet extends HttpServlet {

    private static final String INDEX_JSP = "/pages/index.jsp";
    private static final String UPDATE_JSP = "/pages/update.jsp";
    private List<User> listUsers;
    private CRUD crud;
    private String id;
    private String name;
    private String email;

    @Override
    public void init() throws ServletException {
        crud = new CRUD();
        //listUsers = new CopyOnWriteArrayList<>();
    }

    @Override
    public void destroy() {
        //MySQLConnector.closeThreadsClassMySQLConnector();
        //crud = null;
        //listUsers = null;
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("user", listUsers);
        request.getRequestDispatcher(INDEX_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF8");
        String action = request.getServletPath();

        switch (action) {
            case "/add":                                   // добавление пользователя
                newUser(request, response);
                break;
            case "/get":                                   // вывод данных пользователя
                getDataById(request, response);
                break;
            case "/get_all":                               // вывод данных всех пользователей
                getDataAllUsers(request, response);
                break;
            case "/update":                                // редактирование по id
                updateData(request, response);
                break;
            case "/delete":                                // удаление пользователя по id
                deleteById(request, response);
                break;
            case "/delete_all":                            // удаление всех пользователя
                deleteAll(response);
                break;
            default:                                       // вывод списка
                listUsers(request, response);
                break;
        }
        doGet(request, response);
    }

    private void newUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        name = req.getParameter("name");
        email = req.getParameter("email");
        User newUser = new User(name, email);
        crud.createUser(newUser);
        resp.sendRedirect("list");
        //doGet(req,resp);
    }

    private void getDataById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        id = req.getParameter("id");
        User user = crud.getUserById(Integer.parseInt(id));
        RequestDispatcher dispatcher = req.getRequestDispatcher(INDEX_JSP);
        req.setAttribute("user", user);
        dispatcher.forward(req, resp);
    }

    private void getDataAllUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(INDEX_JSP);
        dispatcher.forward(req, resp);
    }

    private void updateData(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        id = req.getParameter("id");
        name = req.getParameter("name");
        email = req.getParameter("email");
        User user = new User(Integer.parseInt(id), name, email);
        crud.updateUser(user);
        req.setAttribute("user", user);
        req.getRequestDispatcher(UPDATE_JSP).forward(req, resp);
        resp.sendRedirect("list");
    }

    private void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        id = req.getParameter("id");
        crud.deleteUserById(Integer.parseInt(id));
        resp.sendRedirect("list");
    }

    private void deleteAll(HttpServletResponse resp) throws IOException {
        crud.deleteAllUsers();
        resp.sendRedirect("list");
    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        listUsers = crud.getDataAllUsers();
        req.setAttribute("listUser", listUsers);
        RequestDispatcher dispatcher = req.getRequestDispatcher(INDEX_JSP);
        dispatcher.forward(req, resp);
    }

}
