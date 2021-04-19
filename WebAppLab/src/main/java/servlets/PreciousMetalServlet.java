package servlets;

import models.exceptions.ExceptionMessage;
import models.units.Cryptocurrency;
import models.units.PreciousMetal;
import services.CryptocurrencyService;
import services.PreciousMetalService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "PreciousMetalServlet")
public class PreciousMetalServlet extends HttpServlet {

    private void delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String uuid = request.getParameter("uuid");
        PreciousMetalService.sharedInstance().delete(uuid);
        doGet(request, response);
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String uuid = request.getParameter("uuid");
        PreciousMetal element = PreciousMetalService.sharedInstance().getElement(uuid);
        request.setAttribute("element", element);
        request.setAttribute("type", "edit");
        request.getRequestDispatcher("preciousmetal/edit.jsp").forward(request, response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.getRequestDispatcher("preciousmetal/new.jsp").forward(request, response);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String uuid = request.getParameter("uuid");
        String name = request.getParameter("name");
        double high52week = Double.parseDouble(request.getParameter("high52week"));
        double low52week = Double.parseDouble(request.getParameter("low52week"));
        double current = Double.parseDouble(request.getParameter("current"));
        String code = request.getParameter("code");
        PreciousMetalService.sharedInstance().update(new PreciousMetal(uuid, name, high52week, low52week, current, code));
        doGet(request, response);
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String name = request.getParameter("name");
        double high52week = Double.parseDouble(request.getParameter("high52week"));
        double low52week = Double.parseDouble(request.getParameter("low52week"));
        double current = Double.parseDouble(request.getParameter("current"));
        String code = request.getParameter("code");
        PreciousMetalService.sharedInstance().create(new PreciousMetal(UUID.randomUUID().toString(), name, high52week, low52week, current, code));
        doGet(request, response);
    }

    private void task1(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<PreciousMetal> list = PreciousMetalService.sharedInstance().task1();
        request.setAttribute("elements", list);
        request.getRequestDispatcher("preciousmetal/list.jsp").forward(request, response);
    }

    private void task2(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<PreciousMetal> list = PreciousMetalService.sharedInstance().task2();
        request.setAttribute("elements", list);
        request.getRequestDispatcher("preciousmetal/list.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            switch (request.getParameter("action")){
                case "delete":
                    delete(request, response);
                    break;
                case "edit":
                    edit(request, response);
                    break;
                case "new":
                    add(request, response);
                    break;
                case "save":
                    save(request, response);
                    break;
                case "create":
                    create(request, response);
                    break;
                case "task1":
                    task1(request, response);
                    break;
                case "task2":
                    task2(request, response);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("error", ExceptionMessage.get(e));
            request.getRequestDispatcher("Error.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<PreciousMetal> list = PreciousMetalService.sharedInstance().getElements();
            request.setAttribute("elements", list);
            request.getRequestDispatcher("preciousmetal/list.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", ExceptionMessage.get(e));
            request.getRequestDispatcher("Error.jsp").forward(request, response);
        }
    }
}
