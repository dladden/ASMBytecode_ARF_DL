import static utils.Utilities.writeFile;

import org.objectweb.asm.*;
import org.objectweb.asm.Opcodes;

public class Compare{

    public static void main(String[] args){

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V11, Opcodes.ACC_PUBLIC,"Comparison", null, "java/lang/Object",null);
        
        {
        	/*
        	 * Problem 4: Compare two numbers (I, L, and D) to determine which is bigger/smaller.
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

        {
        	/*
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
             * These sections use we use method visitLdcinsn to load a constants. 
             * This visit instruction is used for integers longs, and doubles. It first starts by
             * Initializing two integers 10 and 10 into location 4 and 2 from the stack and store them load them
             * then compares them  them using opcode IF_ICMOGT (If Greater then) if integer is it goes to label 1
             * which then out-prints the value. If not it checks if they equal using opcode IF_ICMPEQ
             * 
             */
            mv.visitLdcInsn((Integer)10);
            mv.visitVarInsn(Opcodes.ISTORE,4);
            mv.visitLdcInsn((Integer)10);
            mv.visitVarInsn(Opcodes.ISTORE,2);
            
            mv.visitVarInsn(Opcodes.ILOAD,4);
            mv.visitVarInsn(Opcodes.ILOAD,2);
            
            mv.visitJumpInsn(Opcodes.IF_ICMPGT, lab1);
            mv.visitVarInsn(Opcodes.ILOAD,4);
            mv.visitVarInsn(Opcodes.ILOAD,2);
            mv.visitJumpInsn(Opcodes.IF_ICMPEQ, lab3);
            
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
            
            mv.visitLabel(lab2);
            
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(Opcodes.ILOAD, 3);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
            
            mv.visitLdcInsn((Double)10.00);
            mv.visitVarInsn(Opcodes.DSTORE, 5);
            mv.visitLdcInsn((Double)11.00);
            mv.visitVarInsn(Opcodes.DSTORE, 7);
            mv.visitVarInsn(Opcodes.DLOAD, 5);
            mv.visitVarInsn(Opcodes.DLOAD, 7);
            mv.visitInsn(Opcodes.DCMPG);
            mv.visitVarInsn(Opcodes.ISTORE, 11);
            
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(Opcodes.ILOAD, 11);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
            
            mv.visitLdcInsn((Long)11l);
            mv.visitVarInsn(Opcodes.LSTORE, 12);
            mv.visitLdcInsn((Long)10l);
            mv.visitVarInsn(Opcodes.LSTORE, 14);
            mv.visitVarInsn(Opcodes.LLOAD, 12);
            mv.visitVarInsn(Opcodes.LLOAD, 14);
            mv.visitInsn(Opcodes.LCMP);
            mv.visitVarInsn(Opcodes.ISTORE, 16);
            
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(Opcodes.ILOAD, 16);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
            
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(0,0);
            mv.visitEnd();
        }

        cw.visitEnd();

        byte[] b = cw.toByteArray();

        writeFile(b,"Comparison.class");
        
        System.out.println("Done!");
    }//end main
}//end class