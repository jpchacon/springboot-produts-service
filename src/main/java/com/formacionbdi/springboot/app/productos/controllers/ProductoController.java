package com.formacionbdi.springboot.app.productos.controllers;

import com.formacionbdi.springboot.app.productos.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.service.IProductoService;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
public class ProductoController {

    private IProductoService productoService;
    private Environment env;

    /*@Value("${server.port}")*/
    //private Integer port;

    public ProductoController(IProductoService productoService, Environment env) {
        this.productoService = productoService;
        this.env = env;
    }

    @GetMapping("/")
    public List<Producto> listar() {
        return productoService.findAll().stream().map(producto -> {
                    producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
                    //producto.setPort(port);
                    return producto;
                }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Producto detalle(@PathVariable Long id) throws InterruptedException {

        if(id.equals(10L)){
            throw new IllegalStateException("Producto no encontrado!");
        }

        if(id.equals(7L)){
            TimeUnit.SECONDS.sleep(5L);
        }
        Producto producto = productoService.findById(id);
        producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
        //producto.setPort(port);
        return producto;
    }
}

