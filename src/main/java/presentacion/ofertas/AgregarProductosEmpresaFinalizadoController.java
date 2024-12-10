package presentacion.ofertas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import modelo.authService.AuthServiceColaboracion;
import modelo.colaboracion.Oferta;
import modelo.colaboracion.TipoOferta;
import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.security.authentication.AuthorizationService;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioOfertas;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AgregarProductosEmpresaFinalizadoController implements Handler {

    private RepositorioOfertas repoOfertas;

    public AgregarProductosEmpresaFinalizadoController(RepositorioOfertas repoOfertas) {
        this.repoOfertas = repoOfertas;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {

        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }

        String nombre = Objects.requireNonNull(context.formParam("nombre"));
        String tipo = Objects.requireNonNull(context.formParam("tipo"));
        String descripcion = Objects.requireNonNull(context.formParam("descripcion"));
        Double puntos = Double.parseDouble(Objects.requireNonNull(context.formParam("puntos")));
        List<UploadedFile> uploadedFiles = context.uploadedFiles("file");

        UploadedFile file = uploadedFiles.get(0);
        String fileName = file.filename();
        System.out.println("Received file: " + fileName);

        TipoOferta tipoOferta;

        if(tipo.equals("producto")){
            tipoOferta = TipoOferta.PRODUCTO;
        }else{
            tipoOferta = TipoOferta.SERVICIO;
        }


        File archivo = new File("src/main/resources/uploads/" + file.filename());


        try {
            //SE CARGA EN LA BASE Y DESP EL ARCHIVO
            Integer idPersona = context.sessionAttribute("idPersona");
            AuthServiceColaboracion.registrarColaboracionRecompensa(idPersona,nombre,descripcion,tipoOferta,puntos,fileName);
            FileUtils.copyInputStreamToFile(file.content(), archivo);

        } catch (Exception e) {
            //Arreglar con un error mas apropiado
            model.put("errorLogin", "Error en el CSV");
            context.sessionAttribute("model", model);
            context.redirect("/cargarCSV");
        }

        //Parsear la oferta

        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        context.render("templates/graciasOferta.mustache", model);

    }

}

