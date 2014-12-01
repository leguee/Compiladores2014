 .386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.DATA
TITULO DB "Mensaje" , 0
OVERFLOW_EN_PRODUCTO_MENSAJE DB "Overflow en producto" , 0
VECTOR_FUERA_DE_RANGO_MENSAJE DB "Vector fuera de rango" , 0

_@cero DQ 0.0
_@max_doble DQ 1.7976931348623156E308
_@max_entero DW 32767
_@aux49 DW ?
_@aux48 DW ?
_@aux47 DW ?
_@aux46 DW ?
_@aux45 DW ?
_@aux44 DW ?
_@aux43 DW ?
_@aux42 DW ?
_@aux41 DW ?
_@aux40 DW ?
_entero56 DW 44
_@aux53 DW ?
_entero50 DW 43
_@aux52 DW ?
_@aux51 DW ?
_entero54 DW 41
_vect DW 43 DUP ( 0 )
_vectLimInf DW 1
_vectLimSup DW 43
_@aux50 DW ?
_entero52 DW 3
_entero48 DW 1
.CODE
START:
SUB _entero52, 1
MOV _@aux40, _entero52
MUL _@aux40,2
MOV _@aux41, _@aux40
ADD _@aux41,0
MOV _@aux42, _@aux41
MOV _@aux43 , _@aux42
DIV _@aux42 , 2
ADD _@aux42 , _vectLimInf
CMP _@aux42 , _vectLimSup
JB VECTOR_FUERA_DE_RANGO
CMP _vectLimInf , _@aux42
JB VECTOR_FUERA_DE_RANGO 

SUB _entero54, 1
MOV _@aux44, _entero54
MUL _@aux44,2
MOV _@aux45, _@aux44
ADD _@aux45,0
MOV _@aux46, _@aux45
MOV _@aux47 , _@aux46
DIV _@aux47 , 2
ADD _@aux47 , _vectLimInf
CMP _@aux47 , _vectLimSup
JB VECTOR_FUERA_DE_RANGO
CMP _vectLimInf , _@aux47
JB VECTOR_FUERA_DE_RANGO 

MOV _@aux47, [_vect + _@aux46 ]
SUB _entero56, 1
MOV _@aux48, _entero56
MUL _@aux48,2
MOV _@aux49, _@aux48
ADD _@aux49,0
MOV _@aux50, _@aux49
MOV _@aux51 , _@aux50
DIV _@aux51 , 2
ADD _@aux51 , _vectLimInf
CMP _@aux51 , _vectLimSup
JB VECTOR_FUERA_DE_RANGO
CMP _vectLimInf , _@aux51
JB VECTOR_FUERA_DE_RANGO 

MOV _@aux51, [_vect + _@aux50 ]
ADD _@aux47,_@aux51
MOV _@aux52, _@aux47
ADD _@aux52,_entero48
MOV _@aux53, _@aux52
MOV [_vect + _@aux43 ] , _@aux53
SALIR:
invoke ExitProcess, 0
OVERFLOW_EN_PRODUCTO:
invoke MessageBox, NULL, addr OVERFLOW_EN_PRODUCTO_MENSAJE, addr TITULO , MB_OK 
JMP SALIR
VECTOR_FUERA_DE_RANGO:
invoke MessageBox, NULL, addr VECTOR_FUERA_DE_RANGO_MENSAJE, addr TITULO , MB_OK 
JMP SALIR
END START
