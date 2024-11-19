package CLasses;

public class Contato {
    protected long Id;
    protected String Email;
    protected String Telefone;

    public Contato(long id, String Email, String Telefone){
        this.Id = id;
        this.Email = Email;
        this.Telefone = Telefone;
    }

    public long getId() { return Id; }
    public void setId(long id) { this.Id = id; }

    public String getEmail() { return Email; }
    public void setEmail(String Email) { this.Email = Email; }

    public String getTelefone() { return Telefone; }
    public void setTelefone(String Telefone) { this.Telefone = Telefone; }
}
