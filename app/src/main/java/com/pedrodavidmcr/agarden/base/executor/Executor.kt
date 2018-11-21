package com.pedrodavidmcr.agarden.base.executor

import com.pedrodavidmcr.agarden.base.domain.usecase.UseCase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

object Executor {
  infix fun execute(useCase: UseCase) {
    doAsync {
      val result: Result = useCase.execute()
      uiThread {
        result()
      }
    }
  }
}