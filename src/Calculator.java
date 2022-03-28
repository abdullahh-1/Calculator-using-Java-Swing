import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.*;

public class Calculator extends JFrame implements ActionListener{
    private JButton[] button = new JButton[24];
    private Font fmain, fsec;
    private JLabel main, sec;
    private String main_s, sec_s, temp, operator, hidden;
    int flag;

    Calculator() {
        setSize(420,570);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setTitle("Calculator");

        flag = 0;
        sec_s = "";
        main_s = "";
        fmain = new Font("Segoe UI Light", Font.BOLD, 28);
        fsec = new Font("Segoe UI Light", Font.BOLD, 14);
        main = new JLabel("", JLabel.RIGHT);
        sec = new JLabel("", JLabel.RIGHT);
        add(main);
        main.setBounds(22, 80, 360, 50);
        main.setFont(fmain);
        add(sec);
        sec.setBounds(22, 20, 360, 40);
        sec.setFont(fsec);
        sec.setText("");

        for (int i = 0; i < 10; i++) {
            button[i] = new JButton("" + i);
        }
        button[0].setBounds (112,450,90,60);
        button[1].setBounds (22,390,90,60);
        button[2].setBounds (112, 390,90,60);
        button[3].setBounds (202,390,90,60);
        button[4].setBounds (22,330,90,60);
        button[5].setBounds (112,330,90,60);
        button[6].setBounds (202,330,90,60);
        button[7].setBounds (22,270,90,60);
        button[8].setBounds (112,270,90,60);
        button[9].setBounds (202,270,90,60);
        button[10] = new JButton("⌫");
        button[10].setBounds (202,210,90,60);
        button[11] = new JButton("CE");
        button[11].setBounds (22,210,90,60);
        button[12] = new JButton ("C");
        button[12].setBounds (112,210,90,60);
        button[13] = new JButton("inv");
        button[13].setBounds (292,150,90,60);
        button[14] = new JButton ("√");
        button[14].setBounds (112,150, 90,60);
        button[15] = new JButton("÷");
        button[15].setBounds (292,210,90,60);
        button[16] = new JButton("%");
        button[16].setBounds (22,150,90,60);
        button[17] = new JButton("×");
        button[17].setBounds (292,270,90,60);
        button[18] = new JButton ("x2");
        button[18].setBounds (202,150,90,60);
        button[19] = new JButton("-");
        button[19].setBounds (292,330,90,60);
        button[20] = new JButton(".");
        button[20].setBounds (202,450,90,60);
        button[21] = new JButton("+");
        button[21].setBounds (292,390,90,60);
        button[22] = new JButton("=");
        button[22].setBounds (292,450,90,60);
        button[23] = new JButton("±");
        button[23].setBounds(22, 450, 90, 60);

        for(int i=0; i<24; i++) {
            add (button[i]);
        }

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        for (int i = 0; i < 24; i++) {
            button[i].addActionListener(this);
        }
    }

    private double operate (double a, double b, int o) throws Exception {
        BigDecimal x;
        double result;
        if (sec_s.charAt(o) == '+') {
            x = BigDecimal.valueOf(a + b);
        }
        else if (sec_s.charAt(o) == '-') {
            x = BigDecimal.valueOf(a - b);
        }
        else if (sec_s.charAt(o) == '×') {
            x = BigDecimal.valueOf(a * b);
        }
        else if (sec_s.charAt(o) == '÷') {
            x = BigDecimal.valueOf(a / b);
        }
        else {
            return a;
        }
        result = x.doubleValue();
        return result;
    }

    private void BinaryOperation () {
        if (!sec_s.isEmpty()) {
            try {
                double result = 0.0, num0, num1;
                String[] operand = new String[2];
                operand[0] = operand[1] = "";
                int j = 0, op = 0;
                for (int i = 0; i < sec_s.length(); i++) {
                    if (((48 <= sec_s.charAt(i)) && (sec_s.charAt(i) <= 57)) || sec_s.charAt(i) == '.' || i == 0)
                        operand[j] += (sec_s.charAt(i));
                    else {
                        if (j == 0) {
                            j = 1;
                            op = i;
                        } else {
                            num0 = Double.parseDouble(String.valueOf(operand[0]));
                            num1 = Double.parseDouble(String.valueOf(operand[1]));
                            result = operate(num0, num1, op);
                            operand[0] = "" + result;
                        }
                    }
                }
                if (main_s.isEmpty()) {
                    main_s = operand[0];
                } else {
                    num0 = Double.parseDouble(String.valueOf(operand[0]));
                    num1 = Double.parseDouble(String.valueOf(main_s));
                    result = operate(num0, num1, op);
                    main_s = "" + BigDecimal.valueOf(result);
                }
                sec_s = "";
                sec.setText(sec_s);
                main.setText(main_s);
            } catch (Exception e) {
                main_s = "";
                sec_s = "";
                sec.setText("");
                main.setText("Math Error!");
            }
        }
    }

    private BigDecimal unaryOperate (double num, String op) throws Exception {
        if (op.equals("±"))
            return BigDecimal.valueOf((num * -1));
        else if (op.equals("√"))
            return BigDecimal.valueOf((Math.sqrt(num)));
        else if (op.equals("x2"))
            return BigDecimal.valueOf((Math.pow(num, 2)));
        else if (op.equals("inv"))
            return BigDecimal.valueOf(Math.pow(num, -1));
        else if (op.equals("%"))
            return BigDecimal.valueOf((num / 100));
        else
            return BigDecimal.valueOf(0);
    }

    private void UnaryOperations() {
        double result, number;
        String n = "", op = "";
        for (int i = 0 ; i < main_s.length(); i++) {
            if (((48 <= main_s.charAt(i)) && (main_s.charAt(i) <= 57)) || main_s.charAt(i) == '.') {
                n += main_s.charAt(i);
            }
            else {
                op = main_s.substring(i, main_s.length());
                break;
            }
        }
        try {
            result = unaryOperate(Double.parseDouble(n), op).doubleValue();
            main_s = "" + BigDecimal.valueOf(result);
            main.setText(main_s);
            sec_s = "(";
            sec_s += op;
            sec_s += ") ";
            sec_s += n;
            sec.setText(sec_s);
            sec_s = "";
        }
        catch (Exception e) {
            sec_s = main_s = "";
            sec.setText(sec_s);
            main.setText("Math Error!");
        }
    }

    private void Operations() {
        if (temp == "+" || temp == "-" || temp == "÷" || temp == "×") {
            if (flag == 1) {
                hidden += main_s;
                main_s = hidden;
            }
            operator = temp;
            sec_s += main_s;
            sec.setText(sec_s);
            main_s = "";
        }
        else if (temp == "%" || temp == "±" || temp == "x2" || temp == "inv" || temp == "√") {
            if (flag == 1) {
                hidden += main_s;
                main_s = hidden;
            }
            UnaryOperations();
            hidden = main_s;
            main_s = "";
            flag = 1;
        }
        else if (temp == "=") {
            main_s = main_s.substring(0, main_s.length() - 1);
            BinaryOperation();
            hidden = main_s;
            main_s = "";
            flag = 1;
        }
        else if (temp == "C") {
            sec_s = "";
            main_s = "";
            hidden = "";
            sec.setText(sec_s);
            main.setText(main_s);
            flag = 0;
        }
        else if (temp == "CE") {
            main_s = "";
            main.setText(main_s);
        }
        else if (temp == "⌫") {
            main_s = main_s.substring(0, main_s.length() - 2);
            main.setText(main_s);
        }
        else {
            flag = 0;
            main.setText(main_s);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        temp = e.getActionCommand();
        main_s += temp;
        Operations();

    }
}
