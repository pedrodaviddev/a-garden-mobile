package com.pedrodavidmcr.agarden.executor

import com.pedrodavidmcr.agarden.UseCase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

object Executor {
  fun execute(useCase: UseCase) {
    doAsync {
      val result: Result = useCase.execute()
      uiThread {
        result()
      }
    }
  }
}