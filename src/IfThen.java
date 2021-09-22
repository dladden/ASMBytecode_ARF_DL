import static utils.Utilities.writeFile;

import org.objectweb.asm.*;
import org.objectweb.asm.Opcodes;

public class IfThen{

    public static void main(String[] args){

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V11, Opcodes.ACC_PUBLIC,"IfThenElse", null, "java/lang/Object",null);
        
        {
        	/*
        	 * Problem 8: Implement If. . . Then . . . Else
        	 * The method visitor to which this visitor must delegate method calls is the ASM code 
        	 * that generates the classes it visits.
        	 */
			MethodVisitor mv=cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
			mv.visitCode();
			mv.visitVarInsn(Opcodes.ALOAD, 0); //load the first local variable: this
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V",false);
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(1,1);
			mv.visitEnd();
		}

        {	/*
        	 * This method creates the mothodvisor 
        	 */
            MethodVisitor mv=cw.visitMethod(Opcodes.ACC_PUBLIC+Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode(); 
            
            /*
             * three variable labels needed for comparison methods
             */
            var lab1 = new Label();
            var lab2 = new Label();
            var lab3 = new Label();
            
            
            /*
             * Initialize two integers into location 4 and 2 and store them 
             */
            mv.visitLdcInsn((Integer)10);
            mv.visitVarInsn(Opcodes.ISTORE,4);
            mv.visitLdcInsn((Integer)10);
            mv.visitVarInsn(Opcodes.ISTORE,2);
            
            /*
             *Push the two in locations 4 and 2 unto the stack to perform a comparison
             *If the integer is greater 
             */
            mv.visitVarInsn(Opcodes.ILOAD,4);
            mv.visitVarInsn(Opcodes.ILOAD,2);
            mv.visitJumpInsn(Opcodes.IF_ICMPGT, lab1);
            
            /*
             * Next two integers are popped on the stack and checked if they are equal using opcode  IF_ICMPEQ
             */
            mv.visitVarInsn(Opcodes.ILOAD,4);
            mv.visitVarInsn(Opcodes.ILOAD,2);
            mv.visitJumpInsn(Opcodes.IF_ICMPEQ, lab3);
            
            /*
             * opced ICONST_M1 reuruns -1 if integers do not equal next goto label 2 whoch peints
             */
            mv.visitInsn(Opcodes.ICONST_M1);
            mv.visitVarInsn(Opcodes.ISTORE, 3);
            mv.visitJumpInsn(Opcodes.GOTO, lab2);
            
            mv.visitLabel(lab3);
            mv.visitLdcInsn((Integer)0);
            mv.visitVarInsn(Opcodes.ISTORE, 3);
            mv.visitJumpInsn(Opcodes.GOTO, lab2);
            
            
            mv.visitLabel(lab1);
            mv.visitLdcInsn((Integer)1);
            mv.visitVarInsn(Opcodes.ISTORE, 3);
            
            /*
             * 
             */
            mv.visitLabel(lab2);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(Opcodes.ILOAD, 3);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
            
            
            
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(0,0);
            mv.visitEnd();
        }

        cw.visitEnd();

        byte[] b = cw.toByteArray();

        writeFile(b,"IfThenElse.class");
        
        System.out.println("Done!");
    }//end main
}//end class