package complier.LLOneparser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.List;

public class analyzeToken {

    public static List<String> identifier; // ��ʶ���б�
    public static List<String> Constant; // �����б�

    public static boolean isIdentifier(String s) { // ��ʶ���Զ���
        if (!createToken.Letter.contains(s.charAt(0))) {
            return false;
        }
        for (int i = 1; i < s.length(); i++) {
            if (!createToken.Letter.contains(s.charAt(i)) && !createToken.Digit.contains(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isConstant(String s) { // ���ֳ����Զ���
        if (s.charAt(0) == '0') {
            if (s.length() == 1) {
                return true;
            } else {
                return false;
            }
        }
        if (!createToken.Digit1.contains(s.charAt(0))) {
            return false;
        }
        for (int i = 1; i < s.length(); i++) {
            if (!createToken.Digit.contains(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean analyzeToken(String s) { // �ʷ�����������
        identifier = new ArrayList<String>(); // ÿ�ε��ôʷ���������ʱҪ�����³�ʼ�����б�ͻ����ַ���
        Constant = new ArrayList<String>();
        createToken.token = new ArrayList<Token>();
        createToken.line = new ArrayList<Integer>();
        createToken.tokenDisplay = new StringBuffer();
        int line = 1; // ������ʼ��Ϊ1
        String SepString; // s��ȫ�����ַ�����SepString�Ƿָ������ַ���
        StringBuffer SavedString = new StringBuffer(); // SavedString��������ָ��е��ַ���
        Token t; // �½���token����
        Boolean flag = false;//�Ƿ���ע��
        Boolean success = true;//�ʷ������Ƿ�ɹ�
        Boolean charID = false;//�Ƿ����ַ���ʶ��
        for (int i = 0; i < s.length(); i++) { // ��Դ������һ���ַ�һ���ַ��ؽ��ж�ȡ���������������ʣ�Ȼ�������ǵĻ��ڱ�ʾToken
            //����ע�ͷ�
            if (s.charAt(i) == '{') {
                flag = true;
                continue;
            }
            if (flag == true && s.charAt(i) != '}') {
                continue;
            }
            if (flag == true && s.charAt(i) == '}') {
                flag = false;
                continue;
            }
            if (flag == false && s.charAt(i) == '}') {
                createToken.tokenDisplay.append("Error in line " + line + ": unable to recognize '" + '}' + "'.\n");
                success = false;
                SavedString = new StringBuffer();
                continue;
            }
            //�����ַ���ʾ��
            if (s.charAt(i) == '\'') {
                if (createToken.Letter.contains(s.charAt(i + 1)) && s.charAt(i + 2) == '\'') {
                    SepString = SavedString.toString();
                    t = new Token(line, String.valueOf(s.charAt(i + 1)), "letter");
                    createToken.token.add(t);
                    createToken.line.add(line);
                    createToken.tokenDisplay.append(line + "," + String.valueOf(s.charAt(i + 1)) + ",letter" + "\n");
                    i = i + 3;
                } else {
                    createToken.tokenDisplay.append("Error in line " + line + ": unable to recognize '" + '\'' + "'.\n");
                    success = false;
                    SavedString = new StringBuffer();
                    continue;
                }
            }
            //����˫����
            if (s.charAt(i) == '\"') {
                createToken.tokenDisplay.append("Error in line " + line + ": unable to recognize '" + '\"' + "'.\n");
                success = false;
                SavedString = new StringBuffer();
                continue;
            }
            if (s.charAt(i) != ' ' && s.charAt(i) != '\n' && s.charAt(i) != '\t' && s.charAt(i) != '\"' && s.charAt(i) != '\'' && s.charAt(i) != '{' && s.charAt(i) != '}'
                    && !createToken.separator.contains(String.valueOf((s.charAt(i))))) { // ������ַ����Ƿֽ����ֱ��׷�ӵ�SavedString��
                SavedString.append(s.charAt(i));
            } else { // ������ַ��Ƿֽ��
                // ���������ĵ���
                if (SavedString.length() != 0) { // ��������ĵ��ʳ���Ϊ�㣬�������˲��ֶ�ֱ�ӽ������ķֽ�����ɲ���
                    SepString = SavedString.toString();
                    if (Character.isLetter(SepString.charAt(0))) {
                        if (createToken.reservedWord.contains((SepString))) { // ����ָ������ַ�����reserved word����token��tokenDisplay��Ҫ׷��
                            t = new Token(line, SepString, "reserved word");
                            createToken.token.add(t);
                            createToken.line.add(line);
                            createToken.tokenDisplay.append(line + "," + SepString + ",reserved word" + "\n");
                        } else if (isIdentifier(SepString)) { // ����ָ������ַ����Ǳ�ʶ������token��tokenDisplay��Ҫ׷��
                            if (!identifier.contains(SepString)) {
                                identifier.add(SepString); // �����ʶ���б���û�иñ�ʶ�������
                            }
                            t = new Token(line, "ID", SepString);
                            createToken.token.add(t);
                            createToken.line.add(line);
                            createToken.tokenDisplay.append(line + ",ID," + SepString + "\n");
                        } else {
                            // ��*���б�ʶ�Ĵ�����Ϊ�����Ŀ��ܳ���
                            // ************************************************************************************************
                            createToken.tokenDisplay.append("Error in line " + line + ": Unable to recognize \'" + SepString + "\'.\n");
                            success = false;
                            SavedString = new StringBuffer();
                            continue;
                        }
                    } else { // ��������ֳ���
                        if (isConstant(SepString)) {
                            if (!Constant.contains(SepString)) { // ������ֳ����б���û�и����ֳ��������
                                Constant.add(SepString);
                            }
                            t = new Token(line, "INTC", SepString);
                            createToken.token.add(t);
                            createToken.line.add(line);
                            createToken.tokenDisplay.append(line + ",INTC," + SepString + "\n");
                        } else {
                            // ************************************************************************************************
                            createToken.tokenDisplay.append("Error in line " + line + ": Unable to recognize \'" + SepString + "\'.\n");
                            success = false;
                            SavedString = new StringBuffer();
                            continue;
                        }
                    }
                    SavedString = new StringBuffer(); // ���³�ʼ�����Է��뵥�ʵĻ����ַ���
                }
                // �����з�
                if (s.charAt(i) == '\n') {
                    line++; // ���к�+1
                    continue;
                }
                if (s.charAt(i) == ' ') {
                    continue;
                }
                if (s.charAt(i) == '\t') {
                    continue;
                }
                if (s.charAt(i) == ':') {
                    if (s.charAt(++i) == '=') {
                        t = new Token(line, createToken.separatorLEX.get(":="), "separator"); // ����ֽ���Ǹ�ֵ������token��tokenDisplay��Ҫ׷��
                        createToken.token.add(t);
                        createToken.line.add(line);
                        createToken.tokenDisplay.append(line + "," + createToken.separatorLEX.get(":=") + "," + "separator" + "\n");
                        continue;
                    } else {
                        // ************************************************************************************************
                        createToken.tokenDisplay.append("Error in line " + line + ": " + "'=' should follow ':'.\n");
                        success = false;
                        SavedString = new StringBuffer();
                        continue;
                    }
                }
                if (s.charAt(i) == '.') {
                    if ((i + 1) != s.length() && s.charAt(i + 1) == '.') { // �����..����token��tokenDisplay��Ҫ׷��
                        t = new Token(line, createToken.separatorLEX.get(".."), "separator"); // ����ֽ��������������token��tokenDisplay��Ҫ׷��
                        createToken.token.add(t);
                        createToken.line.add(line);
                        createToken.tokenDisplay.append(line + "," + createToken.separatorLEX.get("..") + "," + "separator" + "\n");
                        i++;
                        continue;
                    }
                    // ����ֽ���������÷���������������token��tokenDisplay��Ҫ׷��
                    t = new Token(line, createToken.separatorLEX.get("."), "separator");
                    createToken.token.add(t);
                    createToken.line.add(line);
                    createToken.tokenDisplay.append(line + "," + createToken.separatorLEX.get(".") + "," + "separator" + "\n");
                    if ((i + 1) == s.length() || s.charAt(i + 1) == ' ' || s.charAt(i + 1) == '\n' || s.charAt(i + 1) == '\t') { // ����ѵ�����ĩβ��δ��ĩβ������ַ�Ϊ�ո񡢻س����Ʊ��������ʷ�����
                        // *********************************************************************
                        line++;
                        createToken.tokenDisplay.append(line + ",EOF," + "END" + "\n");
                        if (success) {
                            createToken.tokenDisplay.append("\nLexical analysis successful!");
                        }
                    }
                    continue;
                }
                t = new Token(line, createToken.separatorLEX.get(String.valueOf(s.charAt(i))), "separator"); // �ֽ��
                createToken.token.add(t);
                createToken.line.add(line);
                createToken.tokenDisplay.append(line + "," + createToken.separatorLEX.get(String.valueOf(s.charAt(i))) + "," + "separator" + "\n");
            }
        }
        // ************************************************************************************************
        if (SavedString.length() != 0) { // ���������ʱ������ĵ��ʳ��Ȳ�Ϊ�㣬����Ϊ��Ӧ��Token�����ܴʷ�������ʧ�ܣ���Ϊ����δ�ܳɹ�������
            SepString = SavedString.toString();
            if (Character.isLetter(SepString.charAt(0))) {
                if (createToken.reservedWord.contains((SepString))) { // ����ָ������ַ�����reserved word����token��tokenDisplay��Ҫ׷��
                    t = new Token(line, SepString, "reserved word");
                    createToken.token.add(t);
                    createToken.line.add(line);
                    createToken.tokenDisplay.append(line + "," + SepString + ",reserved word" + "\n");
                } else if (isIdentifier(SepString)) { // ����ָ������ַ����Ǳ�ʶ������token��tokenDisplay��Ҫ׷��
                    if (!identifier.contains(SepString)) {
                        identifier.add(SepString); // �����ʶ���б���û�иñ�ʶ�������
                    }
                    t = new Token(line, "ID", SepString);
                    createToken.token.add(t);
                    createToken.line.add(line);
                    createToken.tokenDisplay.append(line + ",ID," + SepString + "\n");
                } else {
                    // ��*���б�ʶ�Ĵ�����Ϊ�����Ŀ��ܳ���
                    // ************************************************************************************************
                    createToken.tokenDisplay.append("Error in line " + line + ": Unable to recognize \'" + SepString + "\'.\n");
                    success = false;
                    SavedString = new StringBuffer();
                }
            } else { // ��������ֳ���
                if (isConstant(SepString)) {
                    if (!Constant.contains(SepString)) { // ������ֳ����б���û�и����ֳ��������
                        Constant.add(SepString);
                    }
                    t = new Token(line, "INTC", SepString);
                    createToken.token.add(t);
                    createToken.line.add(line);
                    createToken.tokenDisplay.append(line + ",INTC," + SepString + "\n");
                } else {
                    // ************************************************************************************************
                    createToken.tokenDisplay.append("Error in line " + line + ": Unable to recognize \'" + SepString + "\'.\n");
                    success = false;
                    SavedString = new StringBuffer();
                }
            }
            SavedString = new StringBuffer(); // ���³�ʼ�����Է��뵥�ʵĻ����ַ���
        }
        return success;
    }
}
