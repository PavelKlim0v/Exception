package com.teachmeskills.lesson_21.task_2.servlet;

import com.teachmeskills.lesson_21.task_2.service.UserCRUDService;
import com.teachmeskills.lesson_21.task_2.entity.User;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class UserServlet extends HttpServlet {

    private static final String INDEX_JSP = "/pages/index.jsp";
    private static final String UPDATE_JSP = "/pages/update.jsp";
    private List<User> listUsers;
    private UserCRUDService userCrudService;
    private String id;
    private String name;
    private String email;

    @Override
    public void init() {
        userCrudService = new UserCRUDService();
        //listUsers = new CopyOnWriteArrayList<>();
    }

    @Override
    public void destroy() {
        //MySQLConnector.closeThreadsClassMySQLConnector();
        //userCrudService = null;
        //listUsers = null;
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("user", listUsers);
        // TODO: Если пришел айди, то выводим одного. Если без айди, то выводим всех.
        request.getRequestDispatcher(INDEX_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF8");
        String action = request.getServletPath();
        // TODO: Здесь должно быть создание нового юзера с его параметрами. Все что ниже делают методы doDelete(), doPut().

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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
        // TODO: руализовать модификацию Юзера по полученному айдишнику тут. Получаем параметры.
        //  Айди - мандаторен. Все остальные параметры если есть, то обновляем для юзера по айди
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
        // TODO: Удаляем юзера если пришел его айди. Если нет, то удаляем всех
    }

    private void newUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        name = req.getParameter("name");
        email = req.getParameter("email");
        User newUser = new User(name, email);
        userCrudService.createUser(newUser);
        resp.sendRedirect("list");
        //doGet(req,resp);
    }

    private void getDataById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        id = req.getParameter("id");
        User user = userCrudService.getUserById(Integer.parseInt(id));
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
        userCrudService.updateUser(user);
        req.setAttribute("user", user);
        req.getRequestDispatcher(UPDATE_JSP).forward(req, resp);
        resp.sendRedirect("list");
    }

    private void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        id = req.getParameter("id");
        userCrudService.deleteUserById(Integer.parseInt(id));
        resp.sendRedirect("list");
    }

    private void deleteAll(HttpServletResponse resp) throws IOException {
        userCrudService.deleteAllUsers();
        resp.sendRedirect("list");
    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        listUsers = userCrudService.getDataAllUsers();
        req.setAttribute("listUser", listUsers);
        RequestDispatcher dispatcher = req.getRequestDispatcher(INDEX_JSP);
        dispatcher.forward(req, resp);
    }

}
