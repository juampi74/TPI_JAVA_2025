<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="entities.*"%>
<%@ page import="enums.UserRole"%>
<%@ page import="enums.DominantFoot"%>

<!DOCTYPE html>
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.png">
	    <title>Mi Perfil</title>
	    
	    <link href="/style/bootstrap.css" rel="stylesheet">
	    <link href="/style/signin.css" rel="stylesheet">
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
	    
	    <style>
	        .profile-card {
	          background-color: rgba(0, 0, 0, 0.2);
	          border: 1px solid rgba(255, 255, 255, 0.1);
	        }
	        
	        .profile-img {
	          width: 150px;
	          height: 150px;
	          object-fit: contain;
	          border: 3px solid #198754;
	        }
	        
	        .profile-img-placeholder {
			  width: 150px;
			  height: 150px;
			  background-color: #e9ecef;
			  color: #2C3034;
			  font-size: 4rem;
			  border: 3px solid #198754;
			}
	    </style>
	
	    <%
	        User u = (User) session.getAttribute("user");
	        Person p = u.getPerson();
	        UserRole role = u.getRole();
	        
	        Player player = (role == UserRole.PLAYER && p instanceof Player) ? (Player) p : null;
	        Coach coach = (role == UserRole.COACH && p instanceof Coach) ? (Coach) p : null;
	        President president = (role == UserRole.PRESIDENT && p instanceof President) ? (President) p : null;
	    %>
	</head>
	<body style="background-color: #10442E;">
	    <jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
	    
	    <div class="container" style="color: white;">
	        <div class="row">
	            <div class="d-flex justify-content-between my-4 align-items-center">
	                <h2>Mi Perfil</h2>
	                
	                <% 
	                    String flash = (String) request.getAttribute("flash");
	                    String cssClass = (String) request.getAttribute("cssClass");
	                    if (flash != null) { 
	                %>
		                    <div id="flash-message" class="alert <%= cssClass != null ? cssClass : "alert-info" %> alert-dismissible fade show" role="alert">
							    <%= flash %>
							    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
							</div>
	                <% } %>
	            </div>
	
	            <form action="${pageContext.request.contextPath}/my-profile" method="post" enctype="multipart/form-data">
	                <div class="row">
	                    <div class="col-12 col-lg-4 mb-4">
	                        <div class="profile-card rounded-3 p-4 text-center">
	                            <div class="mb-3 d-flex justify-content-center">
								    <% if (p.getPhoto() != null && !p.getPhoto().isEmpty()) { %>
								        <img src="images?id=<%= p.getPhoto() %>" 
								             class="rounded-circle profile-img mb-3" 
								             alt="Foto de Perfil">
								    <% } else { %>
								        <div class="profile-img-placeholder rounded-circle mb-3 d-flex justify-content-center align-items-center">
								            <i class="fas fa-user"></i>
								        </div>
								    <% } %>
								</div>
	                            
	                            <div class="mb-3">
	                                <label for="photo" class="form-label text-white-50 small">Actualizar foto</label>
	                                <input type="file" class="form-control form-control-sm" id="photo" name="photo" accept="image/*">
	                            </div>
	                            
	                            <hr class="border-secondary">
	                            <div class="text-start">
	                                <p class="mb-1"><span class="text-white-50 small">Email</span><br><%= u.getEmail() %></p>
	                                <p class="mb-0"><span class="text-white-50 small">Rol</span><br>
	                                    <span class="badge bg-secondary text-uppercase"><%= role.name() %></span>
	                                </p>
	                            </div>
	                        </div>
	                    </div>
	
	                    <div class="col-12 col-lg-8">
	                        <div class="profile-card rounded-3 p-4 mb-5">
	                            <h4 class="mb-4 text-white-50"><i class="fas fa-user-edit me-2"></i>Datos Personales</h4>
	                            
	                            <div class="row g-3">
	                                <div class="col-md-6">
						                <label class="form-label text-white-50 small">Nombre Completo</label>
						                <div class="d-flex align-items-center bg-dark rounded border border-secondary p-2" style="height: 38px; background-color: rgba(255,255,255,0.05);">
						                    <span class="text-white"><%= p.getFullname() %></span>
						                </div>
						            </div>
	                                <div class="col-md-6">
						                <label class="form-label text-white-50 small">DNI</label>
						                <div class="d-flex align-items-center bg-dark rounded border border-secondary p-2" style="height: 38px; background-color: rgba(255,255,255,0.05);">
						                    <span class="text-white"><%= p.getId() %></span>
						                </div>
						            </div>
	                                <div class="col-md-6">
	                                    <label class="form-label">Fecha de Nacimiento</label>
	                                    <input type="date" name="birthdate" class="form-control" value="<%= p.getBirthdate() %>">
	                                </div>
	                                <div class="col-md-6">
									    <label class="form-label text-white-50 small">Nacionalidad</label>
									    <div class="d-flex align-items-center bg-dark rounded border border-secondary p-2" style="height: 38px; background-color: rgba(255,255,255,0.05);">
									        <img src="<%=request.getContextPath() + "/images?id=" + p.getNationality().getFlagImage()%>" 
									             class="me-2 shadow-sm" 
									             style="width: 25px; height: auto; border-radius: 2px;" 
									             alt="<%= p.getNationality().getName() %>">
									        <span><%= p.getNationality().getName() %></span>
									    </div>
									</div>
	                                <div class="col-12">
									    <label class="form-label">Dirección</label>
									    <input type="text" name="address" class="form-control" 
									           value="<%= (p.getAddress() != null) ? p.getAddress() : "" %>">
									</div>
	                            </div>
	
	                            <hr class="my-4 border-secondary">
	
	                            <% if (role == UserRole.PLAYER && player != null) { %>
    
								    <h4 class="mb-4 text-white-50"><i class="fas fa-running me-2"></i>Datos de Jugador</h4>
								    <div class="row g-3">
								        <div class="col-md-3">
								            <label class="form-label text-white-50 small">N° de Camiseta</label>
								            <div class="d-flex align-items-center bg-dark rounded border border-secondary p-2" style="height: 38px; background-color: rgba(255,255,255,0.05);">
								                <i class="fas fa-tshirt me-2 text-white-50"></i>
								                <span class="text-white fw-bold"><%= (player.getJerseyNumber()) > 0 ? player.getJerseyNumber() : "No asignado" %></span>
								            </div>
								        </div>
								
								        <div class="col-md-3">
								            <label class="form-label">Pie Dominante</label>
								            <select name="dominant_foot" class="form-select border-secondary">
								                <% for (DominantFoot df : DominantFoot.values()) { %>
								                    <option value="<%= df.name() %>" <%= df == player.getDominantFoot() ? "selected" : "" %>><%= df.getDisplayName() %></option>
								                <% } %>
								            </select>
								        </div>
								
								        <div class="col-md-3">
								            <label class="form-label">Altura (mts)</label>
								            <input type="number" step="0.01" name="height" class="form-control"
								                   value="<%= (player.getHeight() != null) ? player.getHeight() : "" %>">
								        </div>
								
								        <div class="col-md-3">
								            <label class="form-label">Peso (kg)</label>
								            <input type="number" step="0.1" name="weight" class="form-control"
								                   value="<%= (player.getWeight() != null) ? player.getWeight() : "" %>">
								        </div>
								    </div>
								
								<% } else if (role == UserRole.COACH && coach != null) { %>
	                                
	                                <h4 class="mb-4 text-white-50"><i class="fas fa-clipboard-list me-2"></i>Datos de Director Técnico</h4>
	                                <div class="row g-3">
	                                    <div class="col-md-6">
										    <label class="form-label">Formación Preferida</label>
										    <input type="text" name="preferred_formation" class="form-control" 
										           value="<%= (coach.getPreferredFormation() != null) ? coach.getPreferredFormation() : "" %>">
										</div>
	                                    <div class="col-md-6">
	                                        <label class="form-label">Licencia</label>
	                                        <input type="text" name="coaching_license" class="form-control"
												   value="<%= (coach.getCoachingLicense() != null) ? coach.getCoachingLicense() : "" %>">
										</div>
	                                    <div class="col-md-6">
	                                        <label class="form-label">Fecha de Obtención Licencia</label>
	                                        <input type="date" name="license_date" class="form-control" value="<%= coach.getLicenseObtainedDate() %>">
	                                    </div>
	                                </div>
	
	                            <% } else if (role == UserRole.PRESIDENT && president != null) { %>
	                                
	                                <h4 class="mb-4 text-white-50"><i class="fas fa-briefcase me-2"></i>Datos de Presidente</h4>
	                                <div class="col-12">
	                                    <label class="form-label">Política de Gestión</label>
	                                    <textarea name="management_policy" class="form-control" rows="3"><%= (president.getManagementPolicy() != null) ? president.getManagementPolicy() : "" %></textarea>
	                                </div>
	                            
	                            <% } %>
	
	                            <div class="mt-5 text-end">
	                                <button type="submit" class="btn btn-success fw-bold px-4">
	                                    Guardar Cambios
	                                </button>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </form>
	        </div>
	    </div>
	
	    <script>
	        setTimeout(function() {
	            var alertElement = document.getElementById('flash-message');
	            if (alertElement) {
	                var alert = new bootstrap.Alert(alertElement);
	                alert.close();
	            }
	        }, 2500);
	    </script>
	</body>
</html>