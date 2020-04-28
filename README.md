# especialistaRest

Subindo ums servidor em python python -m SimpleHTTPServer 8000


no script de criação da tabela do oauth2, algumas observações:

--resource_ids não e mais utilizado
--web123  e a senha dos clients
-authorities, quando quero que o client recebe um authorities para uso no token 
(o client credentials, se a autenticação está por endpoint, vou precisar da authorities, 
caso for inserir mais de uma, separa por , ex: 'CONSULTA_PEDIDO,EDITAR_PEDIDO')

- o autoapprove ele nao mostra a tela que da aprovação ao usuario, 