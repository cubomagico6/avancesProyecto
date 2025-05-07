//src/services/storageService.ts

// Este archivo define funciones genéricas para interactuar con localStorage
// Utiliza genéricos de TypeScript para proporcionar tipado seguro al guardar y recuperar datos

// Función genérica para obtener datos del localStorage
// T es un parámetro de tipo genérico que permite usar esta función con cualquier tipo de datos
// La función devuelve un array del tipo especificado T
export const getData = <T>(key: string): T[] => {
    // Intenta obtener datos de localStorage usando la clave proporcionada
    const data = localStorage.getItem(key);
    
    // Operador ternario que verifica si los datos existen
    // Si existen (data no es null), los convierte de JSON a un objeto JavaScript con el tipo T[]
    // Si no existen, devuelve un array vacío
    return data ? (JSON.parse(data) as T[]) : [];
};

// Función genérica para guardar datos en localStorage
// T es un parámetro de tipo genérico que permite usar esta función con cualquier tipo de datos
// La función no devuelve nada (void) ya que solo realiza una operación de escritura
export const saveData = <T>(key: string, data: T[]): void => {
    // Convierte el array de datos a formato JSON y lo guarda en localStorage
    // bajo la clave proporcionada
    localStorage.setItem(key, JSON.stringify(data));
};