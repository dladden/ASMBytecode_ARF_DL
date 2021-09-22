import static utils.Utilities.writeFile;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Strings {

	public static void main(String[] args) {
	
	 ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
     cw.visit(Opcodes.V11, Opcodes.ACC_PUBLIC,"String", null, "java/lang/Object",null);
     
     {
    	 	/*
    	  	* Problem 2: Declare String Variables
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
          * Thes sections use we use method visitLdcinsn to load a constants. 
          * This visit instruction creates a string "hello" and pops it off the stack to store it
          * in location 1. THen it loads it and and print is out
          */
         mv.visitLdcInsn((String)"Hello");
         mv.visitVarInsn(Opcodes.ASTORE, 1);
         mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
         mv.visitVarInsn(Opcodes.ALOAD, 1);
         mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
         
         mv.visitInsn(Opcodes.RETURN);
         mv.visitMaxs(0,0);
         mv.visitEnd();
     }

     cw.visitEnd();

     /*
      * Bytecode writer for the classfile
      */
     byte[] b = cw.toByteArray();

     writeFile(b,"String.class");
     
     System.out.println("Done!");
	}
	
}
