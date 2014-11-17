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
_entero80 DW 4344
_doble6 DQ 0.1
_e DQ ?
_d DW ?
_c DW ?
_entero78 DW 43
_entero76 DW 5
_entero82 DW 4
_b DW ?
_a DW ?
SALIR:
invoke ExitProcess, 0
OVERFLOW_EN_PRODUCTO:
invoke MessageBox, NULL, addr OVERFLOW_EN_PRODUCTO_MENSAJE, addr TITULO , MB_OK 
JMP SALIR
VECTOR_FUERA_DE_RANGO:
invoke MessageBox, NULL, addr VECTOR_FUERA_DE_RANGO_MENSAJE, addr TITULO , MB_OK 
JMP SALIR
END START
