export interface usuarioRequestDto{
    nombre:string; //más usado para crear el usuario
    username:string;
    correo:string;
    password:string;
    rol:string;
}
export interface usuarioResponseDto{
    id:number; //es más usado para actualizar
    nombre:string;
    username:string;
    correo:string;
    //password:string; //por el momento no se utiliza
    rol:string;
}