package CLasses;


public class Usuario{
    private long id;
    private String Nome;
    private String Email;
    private String Senha;

    public Usuario(long id, String Nome, String Email, String Senha){
        this.id = id;
        this.Nome = Nome;
        this.Email = Email;
        this.Senha = Senha;
    }

    public long getid(){return id;}
    public void setid(){this.id = id;}

    public String getNome(){return Nome;}
    public void setNome(){this.Nome = Nome;}

    public String getEmail(){return Email;}
    public void setEmail(){this.Email = Email;}

    public String getSenha(){return Senha;}
    public void setSenha(){this.Senha = Senha;}
}
