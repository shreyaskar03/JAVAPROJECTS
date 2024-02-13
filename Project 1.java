import java.util.*;
class Questions{//Class to represent quiz questions with attributes such as question text, options, and the correct answer
    String questionText;
    String options[];
    String correctAns;
    Questions next;
    static Questions head;
    Questions(String ques,String op[],String cA)
    {
        this.questionText=ques;
        this.options=op;
        this.correctAns=cA;
        this.next=null;
    }
    void printOptions(String arr[])
    {
        for(int i=0;i<arr.length;i++)
        {
            System.out.println(arr[i]);
        }
    }
    static void questionList()
    {
        Questions question1=new Questions("Who is known as the God of Cricket?",new String[]{"A) Sachin Tendulkar",
        "B) Ricky Ponting",
        "C) Brian Lara",
        "D) Vivian Richards"},"A");
        Questions question2=new Questions("What is the highest individual score by a batsman in Test cricket?",new String[]{"A) 375",
        "B) 501*",
        "C) 400",
        "D) 333"},"B");
        question1.next=question2;
        Questions question3=new Questions("Which country won the first-ever Cricket World Cup held in 1975?",new String[]{"A) Australia",
        "B) West Indies",
        "C) England",
        "D) Pakistan"},"B");
        question2.next=question3;
        Questions question4=new Questions("Who holds the record for the fastest century in One Day Internationals (ODIs)?",new String[]{"A) Ab De Villiers",
        "B) Virat Kohli",
        "C) Rohit Sharma",
        "D) Dawid Malan"},"A");
        question3.next=question4;
        Questions question5=new Questions("In which year was the first-ever Twenty20 (T20) International cricket match played?",new String[]{"A) 2005",
        "B) 2008",
        "C) 2006",
        "D) 2007"},"A");
        question4.next=question5;
        Questions question6=new Questions("Which bowler has taken the most wickets in Test cricket?",new String[]{"A) Shane Warne",
        "B) Muttiah Muralitharan",
        "C) James Anderson",
        "D) Anil Kumble"},"B");
        question5.next=question6;
        Questions question7=new Questions("Which player has scored the most runs in a single edition of the ICC Cricket World Cup?",new String[]{"A) Sachin Tendulkar",
        "B) Ricky Ponting",
        "C) Virat Kohli",
        "D) Vivian Richards"},"C");
        question6.next=question7;
        Questions question8=new Questions("Who was the first cricketer to score a double century in a One Day International (ODI) match?",new String[]{"A) Sachin Tendulkar",
        "B) Fakhar Zaman",
        "C) Virender Sehwag",
        "D) Rohit Sharma"},"A");
        question7.next=question8;
        Questions question9=new Questions("Which player has taken the most wickets in T20 Internationals?",new String[]{"A) Jasprit Bumrah",
        "B) Lasith Malinga",
        "C) Rashid Khan",
        "D) Mustafizur Rahman"},"B");
        question8.next=question9;
        Questions question10=new Questions("What is the highest team total in Test cricket?",new String[]{"A) 935/6",
        "B) 952/6",
        "C) 903/7",
        "D) 876/6"},"B");
        question9.next=question10;
        head=question1;
    }
    public void printQuestion(Questions k)
    {
        System.out.println(k.questionText);
        k.printOptions(k.options);
        System.out.println();
    }
}
class Quiz extends Questions//Develop a class to manage a collection of questions, allowing for easy retrieval during the quiz.
{
    Quiz()
    {
        super("",new String[4],"");
        questionList();
    }
    public int startQuiz()
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("WELCOME TO THE QUIZ OF CRICKET");
        Questions pointer=head;
        int finalScore=0;
        while(pointer!=null)
        {
           printQuestion(pointer);
           System.out.println("Select the Correct Option");
           String k=sc.next().toUpperCase();
           if(k.equals(pointer.correctAns))
           {
              finalScore++;
           }
           if(!k.equals("A")&&!k.equals("B")&&!k.equals("C")&&!k.equals("D"))
           {
             System.out.println("Please Select A Valid Option and Try Again!!!");
             continue;
           }
           pointer=pointer.next;
        }
        return finalScore;
    }
    public static void main(String args[])
    {
        Quiz obj=new Quiz();
        System.out.println("Your Final Score is= "+obj.startQuiz());
    }
}

//This entire code involves the usage of Linked-Lists....
