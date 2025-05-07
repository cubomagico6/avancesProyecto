// Importamos los módulos necesarios de React
import React, { createContext, useState, useContext, useEffect, ReactNode } from 'react';

// Definimos la interfaz para el tipo de datos del contexto de autenticación
// Esta interfaz especifica qué propiedades y métodos estarán disponibles para los componentes consumidores
interface AuthContextType {
    isAuthenticated: boolean;        // Estado de autenticación (true = usuario autenticado)
    login: (user: string, pass: string) => boolean;  // Función para iniciar sesión que devuelve éxito/fracaso
    logout: () => void;              // Función para cerrar sesión
}

// Creamos el contexto de autenticación con un valor inicial undefined
// Este contexto almacenará y proporcionará el estado de autenticación a toda la aplicación
const AuthContext = createContext<AuthContextType | undefined>(undefined);

// --- IMPORTANT: Hardcoded credentials for DEMO ONLY ---
// --- In a real app, use a secure backend authentication ---
// Definimos credenciales hardcodeadas solo para demo (NO USAR EN PRODUCCIÓN)
const ADMIN_USER = 'admin';           // Nombre de usuario para la demo
const ADMIN_PASS = '123';             // Contraseña para la demo
const AUTH_STORAGE_KEY = 'barber_admin_auth';  // Clave para almacenar el estado en localStorage

// Definimos la interfaz para las props que recibirá el proveedor de autenticación
interface AuthProviderProps {
    children: ReactNode;             // Los componentes hijos que serán envueltos por el proveedor
}

// Componente proveedor de autenticación que gestiona el estado de autenticación
// Este componente envuelve la aplicación y proporciona el contexto de autenticación
export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
    // Estado para controlar si el usuario está autenticado
    // Usamos una función inicializadora para cargar el estado desde localStorage al montar el componente
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(() => {
        // Verificamos localStorage al cargar inicialmente el componente
        return localStorage.getItem(AUTH_STORAGE_KEY) === 'true';
    });

    // Efecto que se ejecuta cuando cambia el estado de autenticación
    // Sincroniza el estado con localStorage para persistencia entre recargas
    useEffect(() => {
        // Guardamos el estado de autenticación en localStorage cuando cambia
        localStorage.setItem(AUTH_STORAGE_KEY, String(isAuthenticated));
    }, [isAuthenticated]);  // Este efecto se ejecuta cada vez que isAuthenticated cambia

    // Función para intentar iniciar sesión con un usuario y contraseña
    const login = (user: string, pass: string): boolean => {
        // Verificamos si las credenciales coinciden con las hardcodeadas
        if (user === ADMIN_USER && pass === ADMIN_PASS) {
            setIsAuthenticated(true);  // Establecemos el estado como autenticado
            return true;               // Devolvemos éxito
        }
        setIsAuthenticated(false);     // Aseguramos que el usuario esté desconectado en caso de intento fallido
        return false;                  // Devolvemos fracaso
    };

    // Función para cerrar sesión
    const logout = () => {
        setIsAuthenticated(false);     // Establecemos el estado como no autenticado
    };

    // Renderizamos el proveedor de contexto con el valor actual
    // Esto hace que el estado y las funciones estén disponibles para todos los componentes hijos
    return (
        <AuthContext.Provider value={{ isAuthenticated, login, logout }}>
            {children}  {/* Renderizamos los componentes hijos */}
        </AuthContext.Provider>
    );
};

// Hook personalizado para acceder fácilmente al contexto de autenticación
// Este hook simplifica el uso del contexto en otros componentes
export const useAuth = (): AuthContextType => {
    // Obtenemos el contexto actual
    const context = useContext(AuthContext);
    
    // Si el contexto es undefined, significa que el hook se está usando fuera de un AuthProvider
    if (context === undefined) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    
    // Devolvemos el contexto para que los componentes puedan usarlo
    return context;
};