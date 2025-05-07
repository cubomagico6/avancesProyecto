//src/services/serviceService.ts

// Importamos el tipo Service desde el archivo de definición de tipos
// Este tipo define la estructura de datos para un servicio de barbería en la aplicación
import { Service } from '../types/Service';

// Importamos las funciones auxiliares para el manejo del almacenamiento
// getData: recupera datos desde el almacenamiento local
// saveData: guarda datos en el almacenamiento local
import { getData, saveData } from './storageService';

// Definimos una constante para la clave de almacenamiento
// Esta clave se utiliza para identificar donde se guardan los datos de servicios en localStorage
const SERVICE_STORAGE_KEY = 'services_data';

// Función para obtener todos los servicios almacenados
// Retorna un array de objetos Service
export const getServices = (): Service[] => {
    // Llamamos a getData pasando el tipo genérico Service y la clave de almacenamiento
    // Esto recupera y devuelve el array de servicios desde localStorage
    return getData<Service>(SERVICE_STORAGE_KEY);
};

// Función para añadir un nuevo servicio
// Recibe datos de servicio sin ID (usando Omit<Service, 'id'> para omitir el campo id)
// Retorna el nuevo objeto Service completo (con ID generado)
export const addService = (service: Omit<Service, 'id'>): Service => {
    // Obtenemos la lista actual de servicios
    const services = getServices();
    
    // Creamos un nuevo objeto servicio con los datos proporcionados
    // - Generamos un ID único usando crypto.randomUUID()
    // - Convertimos price y estimatedTime a tipo Number para garantizar el formato correcto
    const newService: Service = { 
        ...service, 
        id: crypto.randomUUID(), 
        price: Number(service.price), 
        estimatedTime: Number(service.estimatedTime) 
    };
    
    // Creamos un nuevo array con todos los servicios existentes más el nuevo
    // Usamos el operador spread (...) para crear una copia y evitar mutaciones directas
    const updatedServices = [...services, newService];
    
    // Guardamos el array actualizado en el almacenamiento
    saveData<Service>(SERVICE_STORAGE_KEY, updatedServices);
    
    // Devolvemos el objeto servicio recién creado
    return newService;
};

// Función para actualizar un servicio existente
// Recibe un objeto Service completo con el ID y datos actualizados
// Retorna un booleano indicando si la actualización fue exitosa
export const updateService = (updatedService: Service): boolean => {
    // Obtenemos la lista actual de servicios
    const services = getServices();
    
    // Buscamos la posición del servicio a actualizar usando su ID
    const index = services.findIndex(s => s.id === updatedService.id);
    
    // Si encontramos el servicio (index !== -1)
    if (index !== -1) {
        // Reemplazamos el servicio antiguo con el actualizado
        // Aseguramos que price y estimatedTime sean de tipo Number antes de guardar
        services[index] = {
            ...updatedService,
            price: Number(updatedService.price),
            estimatedTime: Number(updatedService.estimatedTime)
        };
        
        // Guardamos el array modificado en el almacenamiento
        saveData<Service>(SERVICE_STORAGE_KEY, services);
        
        // Devolvemos true para indicar éxito
        return true;
    }
    
    // Si no encontramos el servicio, devolvemos false
    return false;
};

// Función para eliminar un servicio
// Recibe el ID del servicio a eliminar
// Retorna un booleano indicando si la eliminación fue exitosa
export const deleteService = (id: string): boolean => {
    // Obtenemos la lista actual de servicios
    const services = getServices();
    
    // Creamos un nuevo array filtrando y excluyendo el servicio con el ID especificado
    const updatedServices = services.filter(s => s.id !== id);
    
    // Verificamos si realmente eliminamos algún elemento
    // Comparando la longitud del array original con el filtrado
    if (services.length !== updatedServices.length) {
        // Si son diferentes, guardamos el nuevo array filtrado
        saveData<Service>(SERVICE_STORAGE_KEY, updatedServices);
        
        // Devolvemos true para indicar éxito
        return true;
    }
    
    // Si las longitudes son iguales, no se eliminó ningún elemento
    // Devolvemos false para indicar que no se encontró el servicio
    return false;
};