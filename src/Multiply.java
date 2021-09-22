/*
 * Importing ASM package for byte-code manipulation
 */
import static utils.Utilities.writeFile;



import org.objectweb.asm.*;
import org.objectweb.asm.Opcodes;

public class Multiply{

    public static void main(String[] args){

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V11, Opcodes.ACC_PUBLIC,"Multiply", null, "java/lang/Object",null);
        
        {
        	/*
        	 * Problem 1: Multiply two numbers (I, L and D) and store them. 
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
           
            /*
             * These tree sections use we use method visitLdcinsn to load a constants. 
             * This visit instruction is used for ints longs, and doubles. It first starts by
             * Initializing two doubles 7 and 5 into location 1 and 3 from the stack and store them load them
             * then multiply them using opcode DMUL and then stores the product in the location 5 then it prints the result. 
             * This is repeated for integer and long
             */
            mv.visitCode();
            mv.visitLdcInsn((Double)7.00);
            mv.visitVarInsn(Opcodes.DSTORE,1);
            mv.visitLdcInsn((Double)5.00);
            mv.visitVarInsn(Opcodes.DSTORE,3);
            mv.visitVarInsn(Opcodes.DLOAD,1);
            mv.visitVarInsn(Opcodes.DLOAD,3);
            mv.visitInsn(Opcodes.DMUL);
            mv.visitVarInsn(Opcodes.DSTORE,5);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(Opcodes.DLOAD, 5);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(D)V", false);
            
            mv.visitLdcInsn((Integer)8);
            mv.visitVarInsn(Opcodes.ISTORE,6);
            mv.visitLdcInsn((Integer)9);
            mv.visitVarInsn(Opcodes.ISTORE,7);
            mv.visitVarInsn(Opcodes.ILOAD,6);
            mv.visitVarInsn(Opcodes.ILOAD,7);
            mv.visitInsn(Opcodes.IMUL);
            mv.visitVarInsn(Opcodes.ISTORE,8);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(Opcodes.ILOAD, 8);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
            
            mv.visitLdcInsn((Long)7l);
            mv.visitVarInsn(Opcodes.LSTORE,9);
            mv.visitLdcInsn((Long)6l);
            mv.visitVarInsn(Opcodes.LSTORE,11);
            mv.visitVarInsn(Opcodes.LLOAD,9);
            mv.visitVarInsn(Opcodes.LLOAD,11);
            mv.visitInsn(Opcodes.LMUL);
            mv.visitVarInsn(Opcodes.LSTORE,13);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(Opcodes.LLOAD, 13);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V", false);
          
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(0,0);
            mv.visitEnd();
        }

        cw.visitEnd();

        /*
         * Bytecode writer for the classfile
         */
        byte[] b = cw.toByteArray();

        writeFile(b,"Multiply.class");
        
        System.out.println("Done!");
    }//end main
}//end class 