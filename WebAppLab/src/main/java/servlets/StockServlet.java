package servlets;

import models.exceptions.ExceptionMessage;
import models.exceptions.ServletLayerException;
import models.units.Cryptocurrency;
import models.units.Stock;
import services.CryptocurrencyService;
import services.StockService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "StockServlet")
public class StockServlet extends HttpServlet {

    private void delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String uuid = request.getParameter("uuid");
        StockService.sharedInstance().delete(uuid);
        doGet(request, response);
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String uuid = request.getParameter("uuid");
        Stock element = StockService.sharedInstance().getElement(uuid);
        request.setAttribute("element", element);
        request.setAttribute("type", "edit");
        request.getRequestDispatcher("stock/edit.jsp").forward(request, response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.getRequestDispatcher("stock/new.jsp").forward(request, response);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            String uuid = request.getParameter("uuid");
            String name = request.getParameter("name");
            double high52week = Double.parseDouble(request.getParameter("high52week"));
            double low52week = Double.parseDouble(request.getParameter("low52week"));
            double current = Double.parseDouble(request.getParameter("current"));
            String code = request.getParameter("code");
            StockService.sharedInstance().update(new Stock(uuid, name, high52week, low52week, current, code));
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
            StockService.sharedInstance().create(new Stock(UUID.randomUUID().toString(), name, high52week, low52week, current, code));
            doGet(request, response);
        } catch (NumberFormatException e){
            throw new ServletLayerException();
        }
    }

    private void task1(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<Stock> list = StockService.sharedInstance().task1();
        request.setAttribute("elements", list);
        request.getRequestDispatcher("stock/list.jsp").forward(request, response);
    }

    private void task2(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<Stock> list = StockService.sharedInstance().task2();
        request.setAttribute("elements", list);
        request.getRequestDispatcher("stock/list.jsp").forward(request, response);
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
            List<Stock> list = StockService.sharedInstance().getElements();
            request.setAttribute("elements", list);
            request.getRequestDispatcher("stock/list.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", ExceptionMessage.get(e));
            request.getRequestDispatcher("Error.jsp").forward(request, response);
        }
    }
}
