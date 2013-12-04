package com.ipad.background;

import net.sourceforge.floggy.persistence.Persistable;

import com.ipad.io.Repositorio;
import com.ipad.io.binarytree.index.IndiceMatriculaImovel;
import com.ipad.io.binarytree.index.IndiceNumeroHidrometroImovel;
import com.ipad.io.binarytree.index.IndiceQuadraImovel;
import com.ipad.io.binarytree.index.IndiceSequencialRotaImovel;

public class BackgroundTaskCarregarIndices extends BackgroundTask {

	public void performTask() {
		Persistable indiceMatriculaImovel = Repositorio.pesquisarPrimeiroObjeto( IndiceMatriculaImovel.class );;
		Persistable indiceNumeroHidrometroImovel = Repositorio.pesquisarPrimeiroObjeto( IndiceNumeroHidrometroImovel.class );
		Persistable indiceQuadraImovel = Repositorio.pesquisarPrimeiroObjeto( IndiceQuadraImovel.class );
		Persistable indiceSequencialRotaImovel = Repositorio.pesquisarPrimeiroObjeto( IndiceSequencialRotaImovel.class );

		if ( (indiceMatriculaImovel != null) && (indiceNumeroHidrometroImovel != null) && 
				(indiceQuadraImovel != null) && (indiceSequencialRotaImovel != null) ){

			Repositorio.indiceMatriculaImovel =	( IndiceMatriculaImovel )indiceMatriculaImovel;
			
			Repositorio.indiceNumeroHidrometroImovel =	( IndiceNumeroHidrometroImovel )indiceNumeroHidrometroImovel;
			
			Repositorio.indiceQuadraImovel =( IndiceQuadraImovel )indiceQuadraImovel;
			
			Repositorio.indiceSequencialRotaImovel =( IndiceSequencialRotaImovel )indiceSequencialRotaImovel;		

			Repositorio.indicesCarregados = true;

		}else{
			Repositorio.indicesCarregados = false;

		}
	}

	public void taskFinished() {
	}

	public void taskStarted() {
		this.setPriority( Thread.MIN_PRIORITY );		
	}

}
