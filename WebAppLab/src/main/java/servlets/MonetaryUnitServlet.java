package servlets;

import models.exceptions.ExceptionMessage;
import models.exceptions.ServletLayerException;
import models.units.Cryptocurrency;
import models.units.MonetaryUnit;
import services.CryptocurrencyService;
import services.MonetaryUnitService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "MonetaryUnitServlet")
public class MonetaryUnitServlet extends HttpServlet {

    private void delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String uuid = request.getParameter("uuid");
        MonetaryUnitService.sharedInstance().delete(uuid);
        doGet(request, response);
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String uuid = request.getParameter("uuid");
        MonetaryUnit element = MonetaryUnitService.sharedInstance().getElement(uuid);
        request.setAttribute("element", element);
        request.setAttribute("type", "edit");
        request.getRequestDispatcher("monetaryunit/edit.jsp").forward(request, response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.getRequestDispatcher("monetaryunit/new.jsp").forward(request, response);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            String uuid = request.getParameter("uuid");
            String name = request.getParameter("name");
            double high52week = Double.parseDouble(request.getParameter("high52week"));
            double low52week = Double.parseDouble(request.getParameter("low52week"));
            double current = Double.parseDouble(request.getParameter("current"));
            String code = request.getParameter("code");
            String country = request.getParameter("country");
            MonetaryUnitService.sharedInstance().update(new MonetaryUnit(uuid, name, high52week, low52week, current, code, country));
            doGet(request, response);
        } catch (NumberFormatException e){
            throw new ServletLayerException();
        }
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            String name = request.getParameter("name");
            double high52week = Double.parseDouble(request.getParameter("high52week"));
            double low52week = Double.parseDouble(request.getParameter("low52week"));
            double current = Double.parseDouble(request.getParameter("current"));
            String code = request.getParameter("code");
            String country = request.getParameter("country");
            MonetaryUnitService.sharedInstance().create(new MonetaryUnit(UUID.randomUUID().toString(), name, high52week, low52week, current, code, country));
            doGet(request, response);
        } catch (NumberFormatException e){
            throw new ServletLayerException();
        }
    }

    private void task1(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<MonetaryUnit> list = MonetaryUnitService.sharedInstance().task1();
        request.setAttribute("elements", list);
        request.getRequestDispatcher("monetaryunit/list.jsp").forward(request, response);
    }

    private void task2(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<MonetaryUnit> list = MonetaryUnitService.sharedInstance().task2();
        request.setAttribute("elements", list);
        request.getRequestDispatcher("monetaryunit/list.jsp").forward(request, response);
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
            List<MonetaryUnit> list = MonetaryUnitService.sharedInstance().getElements();
            request.setAttribute("elements", list);
            request.getRequestDispatcher("monetaryunit/list.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", ExceptionMessage.get(e));
            request.getRequestDispatcher("Error.jsp").forward(request, response);
        }
    }
}
