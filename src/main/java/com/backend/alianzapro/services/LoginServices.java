


package com.backend.alianzapro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importa BCryptPasswordEncoder
import org.springframework.stereotype.Service;


import com.backend.alianzapro.models.Login;
import com.backend.alianzapro.repositories.LoginRepositories;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class LoginServices {

    @Autowired
    private LoginRepositories loginRepository;
    
    // Guardar un nuevo Login
    public Login save(Login login) {
        // Crear una instancia de BCryptPasswordEncoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // Hashear la contraseña
        String hashedPassword = encoder.encode(login.getPassword());
        // Setear la contraseña hasheada
        login.setPassword(hashedPassword);
        // Guardar el login en el repositorio
        return loginRepository.save(login);
    }

    // Obtener todos los logins
    public List<Login> findAll() {
        return loginRepository.findAll();
    }

    // Encontrar un Login por nombre de usuario
    public Login findByUsername(String username) {
        return loginRepository.findByUsername(username);
    }

    // Eliminar un Login por ID
    public void deleteById(Long id) {
        loginRepository.deleteById(id);
    }

// Verificar las credenciales del usuario

  @Transactional
   public boolean validateUser(String username, String password) {
        // Aquí debes obtener el usuario desde tu base de datos
        Login login = loginRepository.findByUsername(username);

        if (login == null) {
            return false; // Usuario no encontrado
        }
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, login.getPassword());
    }

   public List<Login> getAllCuentas() {
        return loginRepository.findAll();
    }
  

   

    // Método para obtener el ID del usuario basado en su nombre de usuario
    public Long getUserIdByUsername(String username) {
        Login user = loginRepository.findByUsername(username);  // Buscamos el usuario por nombre
        if (user != null) {
            return user.getId();  // Si el usuario existe, devolvemos su ID
        }
        return null;  // Si no se encuentra, devolvemos null
    }
    
}
