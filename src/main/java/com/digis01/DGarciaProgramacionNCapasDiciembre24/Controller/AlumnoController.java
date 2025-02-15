package com.digis01.DGarciaProgramacionNCapasDiciembre24.Controller;

import com.digis01.DGarciaProgramacionNCapasDiciembre24.ML.Alumno;
import com.digis01.DGarciaProgramacionNCapasDiciembre24.ML.AlumnoDireccion;
import com.digis01.DGarciaProgramacionNCapasDiciembre24.ML.Result;
import com.digis01.DGarciaProgramacionNCapasDiciembre24.ML.Semestre;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/Alumno")
public class AlumnoController {

    String urlBase = "http://localhost:8080";

    @GetMapping
    public String Index(Model model) {

        //RestTemplate - consumo de servicios
        RestTemplate restTemplate = new RestTemplate();
        //

        ResponseEntity<Result<AlumnoDireccion>> response = restTemplate.exchange(
                urlBase + "/Alumnoapi",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<AlumnoDireccion>>() {
        });

        Alumno alumnoBusqueda = new Alumno();
        alumnoBusqueda.Semestre = new Semestre();
        model.addAttribute("alumnoBusqueda", alumnoBusqueda);

        Result<AlumnoDireccion> result = response.getBody();

        response.getStatusCode();

        if (result.correct) {
            model.addAttribute("listaAlumno", result.objects);
        } else {
            model.addAttribute("listaAlumno", null);
        }
        return "AlumnoIndex";
    }

//    @PostMapping
//    public String Index(@ModelAttribute Alumno alumnoBusqueda, Model model) {
//        model.addAttribute("alumnoBusqueda", alumnoBusqueda);
//        //alumnoDAOImplementation.GetAllDinamico(alumnoBusqueda);
//        return "AlumnoIndex";
//    }
//
//    @GetMapping("/form/{idAlumno}")
//    public String Form(@PathVariable int idAlumno, Model model) {
//
//        Result resultSemestre = semestreDAOImplementation.GetAll();
//        model.addAttribute("semestres", resultSemestre.objects);
//
//        model.addAttribute("estados", estadoDAOImplementation.GetAll().objects);
//
//        if (idAlumno == 0) { // agrearlo
//            AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
//            alumnoDireccion.Alumno = new Alumno();
//            alumnoDireccion.Alumno.Semestre = new Semestre();
//            alumnoDireccion.Direccion = new Direccion();
////            alumnoDireccion.Direcciones.Colonia = new Colonia();
//
//            model.addAttribute("alumnoDireccion", alumnoDireccion);
//
//            
//            
//            return "AlumnoForm";
//
//        } else { // lo edito
//            Result result = alumnoDAOImplementation.DireccionesByIdAlumno(idAlumno);
//
//            model.addAttribute("alumnoDireccion", result.object);
//            return "AlumnoDirecciones";
//        }
//
//    }
//
//    @PostMapping("/form")
//    public String Form(@Valid @ModelAttribute AlumnoDireccion alumnoDireccion, BindingResult bindingResult, @RequestParam MultipartFile imagenFile, Model model) {
//        //idAlumno == 0  && IdDireccion == 0
//        
//        try {
//        /*validen que sea una imagen*/
//        
//            if (bindingResult.hasErrors()) {
//                model.addAttribute("alumnoDireccion", alumnoDireccion);
//                return "AlumnoForm";
//            }
//        
//            if(!imagenFile.isEmpty()){
//                /*tomar el archivo y proce sarlo a una base64*/
//                byte[] bytes = imagenFile.getBytes();
//                String imagenBase64 = Base64.getEncoder().encodeToString(bytes);
//                alumnoDireccion.Alumno.setImagen(imagenBase64);
//            }
//        
////        alumnoDAOImplementation.Add(alumnoDireccion);
//            alumnoDAOImplementation.AddJPA(alumnoDireccion);
//        
//        } catch (Exception ex){
//            return"Error";
//        }
//        
//        return "redirect:/Alumno";
//    }
//
//    @GetMapping("/formEditable") //idUsuario y idDireccion
//    public String Form(@RequestParam Integer IdAlumno, @RequestParam(required = false) Integer IdDireccion, Model model) {
//
//        if (IdDireccion == null) {
//            AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
//            alumnoDireccion.Alumno = new Alumno();
//            alumnoDireccion.Alumno.setIdAlumno(1);
//            alumnoDireccion.Direccion = new Direccion();
//            alumnoDireccion.Direccion.setIdDireccion(-1);
//            model.addAttribute("alumnoDireccion", alumnoDireccion);
//        } else if (IdDireccion == 0) {
//            AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
//            alumnoDireccion.Alumno = new Alumno();
//            alumnoDireccion.Alumno.setIdAlumno(1);
//            alumnoDireccion.Direccion = new Direccion();
//            alumnoDireccion.Direccion.setIdDireccion(0);
//            model.addAttribute("alumnoDireccion", alumnoDireccion);
//        } else {
//            AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
//            alumnoDireccion.Alumno = new Alumno();
//            alumnoDireccion.Alumno.setIdAlumno(1);
//            alumnoDireccion.Direccion = new Direccion();
//            alumnoDireccion.Direccion.setIdDireccion(1);
//            model.addAttribute("alumnoDireccion", alumnoDireccion);
//        }
//        
//        return "AlumnoForm";
//    }
//
////    @GetMapping("/formEdit?idAlumno=x")
////    public String Form (@ModelAttribute AlumnoDireccion alumnoDireccion){
////        
////        alumnoDAOImplementation.UpdateDireccion(alumnoDireccion);
////        alumnoDAOImplementation.UpdateAlumno
////        return "redirect:/Alumno";
////    }
//    @GetMapping("/Delete/{idAlumno}")
//    public String Delete(@PathVariable int idAlumno) {
//
////        alumnoDAOImplementation.Delete()
//        return "algo"; // redireccionamiento a mi metodo Index
//    }
//
//    @GetMapping("/Direccion/MunicipioByEstado/{IdEstado}")
//    @ResponseBody //
//    public Result MunicipioByEstado(@PathVariable int IdEstado) {
//        Result result = municipioDAOImplementation.MunicipioByEstado(IdEstado);
//
//        return result;
//    }
//
    @GetMapping("/CargaMasiva")
    public String Inicio() {
        return "CargaMasivaIndex";
    }
//
//    @PostMapping("/CargaMasiva")
//    public String CargaMasiva(@RequestParam MultipartFile archivo, Model model, HttpSession session) throws IOException {
//        if (archivo != null && !archivo.isEmpty()) {
//
//            // CargaMasiva.txt
//            // split -> ["CargaMasiva", "txt"]
//            String fileExension = archivo.getOriginalFilename().split("\\.")[1];
//
//            if (fileExension.equals("txt")) {
//                ProcesarArchivo(archivo);
//            } else { // archivo xlsx
//                
//                String root = System.getProperty("user.dir");
//                String path = "src/main/resources/static/archivos";
//                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
//                String absolutePath = root + "/" + path + "/" + fecha + archivo.getOriginalFilename();
//                
//                archivo.transferTo(new File(absolutePath));
//                
//                List<AlumnoDireccion> alumnosDireccion = LecturaArchivo(new File(absolutePath));
//                List<ResultExcel> listaErrores = ValidarInformacion(alumnosDireccion);
//                
//                if (listaErrores.isEmpty()) {
//                        session.setAttribute("urlFile", absolutePath);
//                        model.addAttribute("listaErrores", listaErrores);
//                        model.addAttribute("archivoCorrecto", true);
//                    }else {
//                        model.addAttribute("listaErrores", listaErrores);
//                        model.addAttribute("archivoCorrecto", false);
//                    }
//                
//            }
//
//        }
//
//        return "CargaMasivaIndex";
//    }
//    
//    
//    @GetMapping("/CargaMasiva/procesar")
//    public String Procesar(HttpSession session){
//        /*Recuperar la ruta*/
//        List<AlumnoDireccion> alumnosDireccion = LecturaArchivo(new File(session.getAttribute("urlFile").toString()));
//        /*Recorrer todos los elementos, e insertarlos en la bd*/
//        session.removeAttribute("urlFile");
//        return "CargaMasivaIndex";
//    }
//
//    private boolean ProcesarArchivo(MultipartFile archivo) {
//
//        try {
//
//            InputStream inputStream = archivo.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//            String linea = "";
//
//            while ((linea = bufferedReader.readLine()) != null) {
//                // nombre|apaterno| ....
//                String[] campos = linea.split("\\|");
//
//                AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
//                alumnoDireccion.Alumno = new Alumno();
//                alumnoDireccion.Alumno.setNombre(campos[0]);
//                /*...*/
////                alumnoDireccion.Alumno.setFechaNacimiento(new Date());
//
////                alumnoDAOImplementation.Add(alumnoDireccion);
//            }
//
//        } catch (Exception ex) {
//            return false;
//        }
//
//        return true;
//
//    }
//
//    private List<AlumnoDireccion> LecturaArchivo(File archivo) {
//
//        List<AlumnoDireccion> listaAlumnosDireccion = new ArrayList<>();
//        
//        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo)){
//            Sheet workSheet = workbook.getSheetAt(0);
//            for (Row row : workSheet) {
//                AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
//                alumnoDireccion.Alumno = new Alumno();
//                alumnoDireccion.Alumno.setNombre(row.getCell(0)!= null ? row.getCell(0).toString() : "");
//                alumnoDireccion.Alumno.setApellidoPaterno(row.getCell(1).toString());
//                /*...*/
//                
//                listaAlumnosDireccion.add(alumnoDireccion);
//            }
//        }catch(Exception ex){
//            listaAlumnosDireccion = null;
//        }
//        
//        return listaAlumnosDireccion;
//    }
//    
//    
//    private List<ResultExcel> ValidarInformacion(List<AlumnoDireccion> alumnosDireccion){
//        int fila = 1;
//        List<ResultExcel> listaErrores = new ArrayList<>();
//        for (AlumnoDireccion alumnoDireccion : alumnosDireccion) {
//            
//            if(alumnoDireccion.Alumno.getNombre() == null || alumnoDireccion.Alumno.getNombre() == ""){
//                
//                listaErrores.add(new ResultExcel(fila, alumnoDireccion.Alumno.getNombre(), "Campo obligatorio"));
//            }
////            if(alumnoDireccion.Alumno.getNombre() <- no tenga algo que no se una letra  )
//            
//            fila++;
//        }
//        
//        return listaErrores;
//    }
//
//    @GetMapping("/BajaLogica/{IdAlumno}")
//    @ResponseBody
//    public Result BajaLogica(@PathVariable int IdAlumno){
//        Result result = alumnoDAOImplementation.BajaLogicaJPA(IdAlumno);
//        return result;
//    }
    
    
    @PostMapping("/CargaMasiva")
    public String CargaMasiva(@RequestParam("archivo") MultipartFile archivo){
        
        RestTemplate restTemplate = new RestTemplate();
        try {
            ByteArrayResource byteArrayResource = new ByteArrayResource(archivo.getBytes()){
                @Override
                public String getFilename(){
                    return archivo.getOriginalFilename();
                }
            };
            //collectors 
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("archivo", byteArrayResource);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            
            ResponseEntity<Result<Integer>> response = restTemplate.exchange(
                    urlBase + "/Alumnoapi/CargaMasiva"
                    , HttpMethod.POST
                    ,requestEntity 
                    , new ParameterizedTypeReference<Result<Integer>>(){ 
                    });
            
            System.out.println(response.getStatusCode());
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        
        
        return "";
    }
}
