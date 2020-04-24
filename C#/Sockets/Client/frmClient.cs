using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Windows.Forms;

namespace Sockets
{
    public partial class FrmClient : Form
    {
        public FrmClient()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {            
            // Obtêm o IP do Host
            // Neste caso o IP é : 127.0.0.1 ou localhost
            // Se um host tiver vários endereços irá retornar uma lista de endereços
            IPHostEntry host = Dns.GetHostEntry("localhost");

            // Pega o primeiro endereço da lista de endereços
            IPAddress ipAddress = host.AddressList[0];

            // Estabelece a conexão com o endpoint
            IPEndPoint remoteEP = new IPEndPoint(ipAddress, 8080);

            // Cria socket TCP/IP.    
            var socket = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);

            // Conecta ao endpoint
            socket.Connect(remoteEP);

            richTextBox1.AppendText("CLIENTE: Socket conectado com: " + socket.RemoteEndPoint.ToString());

            // Encode texto digitado na tela em byte array    
            byte[] msg = Encoding.ASCII.GetBytes(txtBMensagem.Text);

            // Enviar os dados pelo socket   
            int numeroBytesEnviados = socket.Send(msg);            

            // Array de bytes para armazenar a resposta do servidor
            byte[] bytes = new byte[1024];

            // Recebe a resposta do servidor   
            int numeroBytesRecebidos = socket.Receive(bytes);
            richTextBox1.AppendText("\n " + Encoding.ASCII.GetString(bytes, 0, numeroBytesRecebidos));

            // Desabilita envios e resposta com o socket
            socket.Shutdown(SocketShutdown.Both);
            socket.Close();            
        }
    }
}
