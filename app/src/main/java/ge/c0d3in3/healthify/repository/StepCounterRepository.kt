package ge.c0d3in3.healthify.repository

import ge.c0d3in3.healthify.model.StepData

class StepCounterRepository(private val userRepository: UserRepository){

    suspend fun getCurrentTargetStep(): Int?{
        if(!userRepository.isUserLoggedIn())
            return null
        return userRepository.getUser().targetStep
    }
    suspend fun getAllSteps(): MutableList<StepData> {
        if(!userRepository.isUserLoggedIn())
            return mutableListOf()
        return userRepository.getUser().steps
    }

    suspend fun addNewStep(stepData: StepData) {
        val user = userRepository.getUser()
        user.steps.add(stepData)
        userRepository.saveUser(user)
    }

    suspend fun getSteps(dateStart: Long, dateEnd: Long? = null): List<StepData> {
        val steps = userRepository.getUser().steps
        return if(dateEnd != null)
            steps.filter { it.timestamp <= dateEnd && it.timestamp >= dateStart }
        else
            steps.filter { it.timestamp in dateStart..System.currentTimeMillis()}
    }

    suspend fun saveSteps(steps: MutableList<StepData>) {
        if(!userRepository.isUserLoggedIn())
            return
        userRepository.updateSteps(steps)
    }

    suspend fun updateSteps(stepData: StepData) {
        val user = userRepository.getUser()
        val steps = user.steps.toMutableList()
        steps.forEachIndexed { idx, it ->
            if(it.timestamp == stepData.timestamp)
                steps[idx] = stepData
        }
        userRepository.updateSteps(steps)
    }
}