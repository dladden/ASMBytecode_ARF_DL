import static utils.Utilities.writeFile;

import org.objectweb.asm.*;
import org.objectweb.asm.Opcodes;

public class While{

    public static void main(String[] args){

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V11, Opcodes.ACC_PUBLIC,"WhileLoop", null, "java/lang/Object",null);
        
        {
			MethodVisitor mv=cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
			mv.visitCode();
			mv.visitVarInsn(Opcodes.ALOAD, 0); //load the first local variable: this
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V",false);
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(1,1);
			mv.visitEnd();
		}

        {
            MethodVisitor mv=cw.visitMethod(Opcodes.ACC_PUBLIC+Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();
         
          
            var label1 = new Label();//Creating a variable label
            
            /*
             * we use method visitLdcinsn to load a constants. This visit instruction is used for
             * integers, longs, floats, doubles, strings, classes, methodhandles and methodtypes.
             * But we use for integers. Additional we use method Visits a local variable instruction.
             * This section shows implementation of a "do while" loop using three variables and simply incrementing in the loop. 
             * Initializing two integers 0 and 3 into location 5 and 6 from the stack and store them load them
             * then divides using opcode DDIV them and then stores the quotient in the location 5 then it prints the result. 
             * 
             */
            mv.visitLdcInsn((Integer)0);//
            mv.visitVarInsn(Opcodes.ISTORE, 5);
            
            mv.visitLdcInsn((Integer)3);
            mv.visitVarInsn(Opcodes.ISTORE, 6);
            
            mv.visitLabel(label1);
            
            mv.visitLdcInsn((Integer)1);
            mv.visitVarInsn(Opcodes.ISTORE, 7);
            mv.visitVarInsn(Opcodes.ILOAD, 7);
            mv.visitVarInsn(Opcodes.ILOAD, 5);
            mv.visitInsn(Opcodes.IADD);
            mv.visitVarInsn(Opcodes.ISTORE, 5);
            
            mv.visitLdcInsn((String)"Hello");
            mv.visitVarInsn(Opcodes.ASTORE, 1);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            
            
            mv.visitVarInsn(Opcodes.ILOAD, 5);
            mv.visitVarInsn(Opcodes.ILOAD,6);
            mv.visitJumpInsn(Opcodes.IF_ICMPLE, label1);
            
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(0,0);
            mv.visitEnd();
        }

        cw.visitEnd();

        byte[] b = cw.toByteArray();

        writeFile(b,"WhileLoop.class");
        
        System.out.println("Done!");
    }//end main
}//end class