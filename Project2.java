import java.util.*;
import java.security.SecureRandom;
public class Project2
{
     public static String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder randomString = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            randomString.append(characters.charAt(randomIndex));
        }

        return randomString.toString();
    }
    public static void main(String args[])
    {
        HashMap<String,String> map=new HashMap<>();
         while(true)
    {
       Scanner sc=new Scanner(System.in);
       System.out.println("You have 3 options-");
       System.out.println("1.Shorten The Url");
       System.out.println("2.Expand the Url");
       System.out.println("Press 3 to Exit!");
       System.out.println("Choose Any One Option");
        int choice=sc.nextInt();
        String k=sc.nextLine();
     switch(choice)
     {
        case 1:
            {
                System.out.println("Enter the Url to be Shortened");
                String str=sc.nextLine();
                String key=generateRandomString(6);
                while(map.containsKey(key))
                {
                    key=generateRandomString(6);
                }
                map.put(key,str);
                System.out.println("www.shortyurl.com/"+key);
                break;
            }
            
        case 2:
            {
                System.out.println("Enter the Url to Be Expanded");
                String str=sc.nextLine();
                String key=str.substring(str.indexOf('/')+1,str.length());
                if(!map.containsKey(key))
                {
                    System.out.println("Url Not Found in The DataBase! Please Check your input and try again!");
                }
                else
                {
                    System.out.println(map.get(key));
                }
                break;
            }
            case 3:
            {
                System.exit(0);
                break;
            }
            default :
            System.out.println("Wrong Input! Please Select the correct Choice!");
     }
    }
    }
}