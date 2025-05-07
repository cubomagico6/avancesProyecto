//src/services/barberService.ts

// Importamos el tipo Barber desde el archivo de definición de tipos
// Este tipo define la estructura de datos para un barbero en la aplicación
import { Barber } from '../types/Barber';

// Importamos las funciones auxiliares para el manejo del almacenamiento
// getData: recupera datos desde el almacenamiento local
// saveData: guarda datos en el almacenamiento local
import { getData, saveData } from './storageService';

// Definimos una constante para la clave de almacenamiento
// Esta clave se utiliza para identificar donde se guardan los datos de barberos en localStorage
const BARBER_STORAGE_KEY = 'barbers_data';

// Función para obtener todos los barberos almacenados
// Retorna un array de objetos Barber
export const getBarbers = (): Barber[] => {
    // Llamamos a getData pasando el tipo genérico Barber y la clave de almacenamiento
    // Esto recupera y devuelve el array de barberos desde localStorage
    return getData<Barber>(BARBER_STORAGE_KEY);
};

// Función para añadir un nuevo barbero
// Recibe datos de barbero sin ID (usando Omit<Barber, 'id'> para omitir el campo id)
// Retorna el nuevo objeto Barber completo (con ID generado)
export const addBarber = (barber: Omit<Barber, 'id'>): Barber => {
    // Obtenemos la lista actual de barberos
    const barbers = getBarbers();
    
    // Creamos un nuevo objeto barbero con los datos proporcionados
    // y generamos un ID único usando crypto.randomUUID()
    const newBarber: Barber = { ...barber, id: crypto.randomUUID() }; // Use built-in UUID generator
    
    // Creamos un nuevo array con todos los barberos existentes más el nuevo
    // Usamos el operador spread (...) para crear una copia y evitar mutaciones directas
    const updatedBarbers = [...barbers, newBarber];
    
    // Guardamos el array actualizado en el almacenamiento
    saveData<Barber>(BARBER_STORAGE_KEY, updatedBarbers);
    
    // Devolvemos el objeto barbero recién creado
    return newBarber;
};

// Función para actualizar un barbero existente
// Recibe un objeto Barber completo con el ID y datos actualizados
// Retorna un booleano indicando si la actualización fue exitosa
export const updateBarber = (updatedBarber: Barber): boolean => {
    // Obtenemos la lista actual de barberos
    const barbers = getBarbers();
    
    // Buscamos la posición del barbero a actualizar usando su ID
    const index = barbers.findIndex(b => b.id === updatedBarber.id);
    
    // Si encontramos el barbero (index !== -1)
    if (index !== -1) {
        // Reemplazamos el barbero antiguo con el actualizado
        // Esto modifica el array original en la posición específica
        barbers[index] = updatedBarber;
        
        // Guardamos el array modificado en el almacenamiento
        saveData<Barber>(BARBER_STORAGE_KEY, barbers);
        
        // Devolvemos true para indicar éxito
        return true;
    }
    
    // Si no encontramos el barbero, devolvemos false
    return false;
};

// Función para eliminar un barbero
// Recibe el ID del barbero a eliminar
// Retorna un booleano indicando si la eliminación fue exitosa
export const deleteBarber = (id: string): boolean => {
    // Obtenemos la lista actual de barberos
    const barbers = getBarbers();
    
    // Creamos un nuevo array filtrando y excluyendo el barbero con el ID especificado
    const updatedBarbers = barbers.filter(b => b.id !== id);
    
    // Verificamos si realmente eliminamos algún elemento
    // Comparando la longitud del array original con el filtrado
    if (barbers.length !== updatedBarbers.length) {
        // Si son diferentes, guardamos el nuevo array filtrado
        saveData<Barber>(BARBER_STORAGE_KEY, updatedBarbers);
        
        // Devolvemos true para indicar éxito
        return true;
    }
    
    // Si las longitudes son iguales, no se eliminó ningún elemento
    // Devolvemos false para indicar que no se encontró el barbero
    return false;
};