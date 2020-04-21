using System;
using System.Net;
using System.Net.Sockets;
using System.Text;

namespace Servidor
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                // Obtêm o IP do Host
                // Neste caso o IP é : 127.0.0.1 ou localhost
                // Se um host tiver vários endereços irá retornar uma lista de endereços
                IPHostEntry host = Dns.GetHostEntry("localhost");

                // Pega o primeiro endereço da lista de endereços
                IPAddress ipAddress = host.AddressList[0];

                // Estabelece a conexão com o endpoint
                IPEndPoint localEndPoint = new IPEndPoint(ipAddress, 8080);

                // Cria um socket que utilizará o protocolo TCP    
                Socket listener = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);

                // O socket criado está sendo associado com o endpoint
                listener.Bind(localEndPoint);

                // Especifica o tamanho da fila de espera, se passar de 10 responderá como ocupado
                listener.Listen(10);

                Console.WriteLine("Aguardando por conexões...");

                // Aceita a conexão do cliente com o servidor
                Socket client = listener.Accept();

                // Dados recebidos do cliente
                string dados = null;

                // Número de bytes que será recuperado, número bairro propositalmente para ver na recuperação dos bytes o loop
                byte[] bytesRecebidos = new byte[256];
                int bytes;

                // Enquanto o servidor tiver bytes para mandar leia
                do
                {
                    // recuperando bytes do servidor
                    bytes = client.Receive(bytesRecebidos, bytesRecebidos.Length, 0);

                    // convertendo bytes recuperados do servidor
                    dados += Encoding.ASCII.GetString(bytesRecebidos, 0, bytes);
                } while (client.Available > 0);

                // Mostra no console dados mandados pelo cliente conectado
                Console.WriteLine(dados);

                // Mensagem a ser retornada para o cliente
                string msgRetorno = "SERVIDOR: Dados recebidos e processados...";

                // Mensagem de retorno sendo convertida para byte array
                byte[] msg = Encoding.ASCII.GetBytes(msgRetorno);

                // Enviando resposta devolta ao cliente
                client.Send(msg);

                // Desliga conexão
                client.Shutdown(SocketShutdown.Both);

                // Fecha conexão
                client.Close();

                Console.WriteLine("Finalizando o servidor...");

            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }

            Console.Read();
        }
    }
}
