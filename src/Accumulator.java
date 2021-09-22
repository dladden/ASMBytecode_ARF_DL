import static utils.Utilities.writeFile;

import org.objectweb.asm.*;
import org.objectweb.asm.Opcodes;

public class Accumulator{

    public static void main(String[] args){

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V11, Opcodes.ACC_PUBLIC,"Accumulate", null, "java/lang/Object",null);
        
        {	
        	/*
        	 * Problem 8: Get input (I or D), from the user, run a loop that adds that number to an.
        	 * accumulator and then prints the result.
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
             * Variable label needed for  methods
             */
            var label1 = new Label();
            
            /*
             * This section use we use method visitLdcinsn to load a constants and Visits a local variable instruction. 
             * It first starts by initializing three integers 0, 0, and 3 into location 3, 5, and 6 from the stack and store them load them
             * then 
             */
            mv.visitLdcInsn((Integer)0);
            mv.visitVarInsn(Opcodes.ISTORE, 3);
            mv.visitLdcInsn((Integer)0);
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
            
            
            mv.visitTypeInsn(Opcodes.NEW, "java/util/Scanner");//Creates the scanner
            mv.visitInsn(Opcodes.DUP);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;");//promts user input
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/Scanner", "<init>", "(Ljava/io/InputStream;)V", false);
            mv.visitVarInsn(Opcodes.ASTORE,1);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "nextInt", "()I", false);
            mv.visitVarInsn(Opcodes.ISTORE, 2);
            
            mv.visitVarInsn(Opcodes.ILOAD, 2);
            mv.visitVarInsn(Opcodes.ILOAD,3);
            mv.visitInsn(Opcodes.IADD);
            mv.visitVarInsn(Opcodes.ISTORE, 3);
            
            
            mv.visitVarInsn(Opcodes.ILOAD, 5);
            mv.visitVarInsn(Opcodes.ILOAD,6);
            mv.visitJumpInsn(Opcodes.IF_ICMPLE, label1);
            
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(Opcodes.ILOAD, 3);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
            
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(0,0);
            mv.visitEnd();
        }

        cw.visitEnd();

        byte[] b = cw.toByteArray();

        writeFile(b,"Accumulate.class");
        
        System.out.println("Done!");
    }//end main
}//end class