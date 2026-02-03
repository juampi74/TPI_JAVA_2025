package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;

@WebFilter("/*")
public class AuthFilter implements Filter {

    public AuthFilter() { }

    public void destroy() { }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        
        String path = req.getRequestURI().substring(req.getContextPath().length());
        
        String action = req.getParameter("action");
        
        boolean isRestrictedAction = false;
        if (action != null) {
            isRestrictedAction = 
                action.equals("add") || 
                action.equals("edit") || 
                action.equals("delete") ||
                action.equals("members") ||
                action.equals("setClassicRival") ||
                action.equals("removeClassicRival") ||
                action.equals("release") ||
                action.equals("extend") ||
                action.equals("setResult") ||
                action.equals("generatePlayoffs") ||
                action.equals("generateNextStage") ||
                action.equals("finishTournament");
        }

        boolean isMixedServlet = 
        	path.startsWith("/actiontournament") ||
            path.startsWith("/actionmatch") ||
        	path.startsWith("/actionclub") ||
            path.startsWith("/actionplayer") ||
            path.startsWith("/actioncoach") ||
            path.startsWith("/actionpresident");
        
        boolean isPublicResource = 
            path.equals("/") ||
            path.equals("/index.html") ||
            path.startsWith("/login") || 
            path.startsWith("/Home") || 
            path.startsWith("/style") ||
            path.startsWith("/assets") ||
            path.startsWith("/images") ||          
            (isMixedServlet && !isRestrictedAction);
        
        
        if (user != null) {
            
        	chain.doFilter(request, response);
            
        } else {
            
            if (isPublicResource) {
            	
                chain.doFilter(request, response);
            
            } else {
                
                String origin = req.getRequestURI();
                
                if (req.getQueryString() != null) {
                
                	origin += "?" + req.getQueryString();
                
                }
                
                session = req.getSession(true);
                
                if (isRestrictedAction) {
                
                	session.setAttribute("flash", "Debés iniciar sesión para realizar cambios");
                
                } else {
                
                	session.setAttribute("flash", "Debes iniciar sesión para acceder a esa sección");
                
                }
                
                session.setAttribute("tempOrigin", origin);

                res.sendRedirect(req.getContextPath() + "/login");

            }
        
        }
    
    }

    public void init(FilterConfig fConfig) throws ServletException { }
    
}