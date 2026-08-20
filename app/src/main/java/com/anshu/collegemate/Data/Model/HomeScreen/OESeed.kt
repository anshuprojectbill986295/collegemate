package com.anshu.collegemate.Data.Model.HomeScreen

data class StudentOE(
    val subjectCode: String,
    val subjectName: String,
    val instructor: String,
    val venue: String,
    val syllabusLink: String
)

object OESeed {
    private val mapping = mapOf(
        // Elements of Mechanical Design (ME-310X)
        "abhay.cse.24@nitap.ac.in" to StudentOE("ME-310X", "Elements of Mechanical Design", "Supreme Das", "Mechanical Block", "https://drive.google.com/file/d/1bJVJCbbS4NEr7yqQg1EisubIDmkib7VI/view?usp=drive_link"),
        "aditya.cse.24@nitap.ac.in" to StudentOE("ME-310X", "Elements of Mechanical Design", "Supreme Das", "Mechanical Block", "https://drive.google.com/file/d/1bJVJCbbS4NEr7yqQg1EisubIDmkib7VI/view?usp=drive_link"),
        "anurag.cse.24@nitap.ac.in" to StudentOE("ME-310X", "Elements of Mechanical Design", "Supreme Das", "Mechanical Block", "https://drive.google.com/file/d/1bJVJCbbS4NEr7yqQg1EisubIDmkib7VI/view?usp=drive_link"),
        "disha.cse.24@nitap.ac.in" to StudentOE("ME-310X", "Elements of Mechanical Design", "Supreme Das", "Mechanical Block", "https://drive.google.com/file/d/1bJVJCbbS4NEr7yqQg1EisubIDmkib7VI/view?usp=drive_link"),
        "fredy.cse.24@nitap.ac.in" to StudentOE("ME-310X", "Elements of Mechanical Design", "Supreme Das", "Mechanical Block", "https://drive.google.com/file/d/1bJVJCbbS4NEr7yqQg1EisubIDmkib7VI/view?usp=drive_link"),
        "nabam.cse.24@nitap.ac.in" to StudentOE("ME-310X", "Elements of Mechanical Design", "Supreme Das", "Mechanical Block", "https://drive.google.com/file/d/1bJVJCbbS4NEr7yqQg1EisubIDmkib7VI/view?usp=drive_link"),
        "ngilyang.cse.24@nitap.ac.in" to StudentOE("ME-310X", "Elements of Mechanical Design", "Supreme Das", "Mechanical Block", "https://drive.google.com/file/d/1bJVJCbbS4NEr7yqQg1EisubIDmkib7VI/view?usp=drive_link"),
        "riya.cse.24@nitap.ac.in" to StudentOE("ME-310X", "Elements of Mechanical Design", "Supreme Das", "Mechanical Block", "https://drive.google.com/file/d/1bJVJCbbS4NEr7yqQg1EisubIDmkib7VI/view?usp=drive_link"),
        "tenzin.cse.24@nitap.ac.in" to StudentOE("ME-310X", "Elements of Mechanical Design", "Supreme Das", "Mechanical Block", "https://drive.google.com/file/d/1bJVJCbbS4NEr7yqQg1EisubIDmkib7VI/view?usp=drive_link"),

        // Innovation, Technology and Entrepreneurship (MH-310X)
        "abhishek.cse.24@nitap.ac.in" to StudentOE("MH-310X", "Innovation, Technology and Entrepreneurship", "M.K.Shome", "CS block", "https://drive.google.com/file/d/1uWNbml9nzmTG2jtqFxDC7kRN-MwLIJTy/view?usp=drive_link"),
        "anshu.cse.24@nitap.ac.in" to StudentOE("MH-310X", "Innovation, Technology and Entrepreneurship", "M.K.Shome", "CS block", "https://drive.google.com/file/d/1uWNbml9nzmTG2jtqFxDC7kRN-MwLIJTy/view?usp=drive_link"),
        "arjun.cse.24@nitap.ac.in" to StudentOE("MH-310X", "Innovation, Technology and Entrepreneurship", "M.K.Shome", "CS block", "https://drive.google.com/file/d/1uWNbml9nzmTG2jtqFxDC7kRN-MwLIJTy/view?usp=drive_link"),
        "divyanshu.cse.24@nitap.ac.in" to StudentOE("MH-310X", "Innovation, Technology and Entrepreneurship", "M.K.Shome", "CS block", "https://drive.google.com/file/d/1uWNbml9nzmTG2jtqFxDC7kRN-MwLIJTy/view?usp=drive_link"),
        "jikke.cse.24@nitap.ac.in" to StudentOE("MH-310X", "Innovation, Technology and Entrepreneurship", "M.K.Shome", "CS block", "https://drive.google.com/file/d/1uWNbml9nzmTG2jtqFxDC7kRN-MwLIJTy/view?usp=drive_link"),
        "nido.cse.24@nitap.ac.in" to StudentOE("MH-310X", "Innovation, Technology and Entrepreneurship", "M.K.Shome", "CS block", "https://drive.google.com/file/d/1uWNbml9nzmTG2jtqFxDC7kRN-MwLIJTy/view?usp=drive_link"),
        "prateek.cse.24@nitap.ac.in" to StudentOE("MH-310X", "Innovation, Technology and Entrepreneurship", "M.K.Shome", "CS block", "https://drive.google.com/file/d/1uWNbml9nzmTG2jtqFxDC7kRN-MwLIJTy/view?usp=drive_link"),
        "rohit.cse.24@nitap.ac.in" to StudentOE("MH-310X", "Innovation, Technology and Entrepreneurship", "M.K.Shome", "CS block", "https://drive.google.com/file/d/1uWNbml9nzmTG2jtqFxDC7kRN-MwLIJTy/view?usp=drive_link"),
        "sagelo.cse.24@nitap.ac.in" to StudentOE("MH-310X", "Innovation, Technology and Entrepreneurship", "M.K.Shome", "CS block", "https://drive.google.com/file/d/1uWNbml9nzmTG2jtqFxDC7kRN-MwLIJTy/view?usp=drive_link"),
        "satam.cse.24@nitap.ac.in" to StudentOE("MH-310X", "Innovation, Technology and Entrepreneurship", "M.K.Shome", "CS block", "https://drive.google.com/file/d/1uWNbml9nzmTG2jtqFxDC7kRN-MwLIJTy/view?usp=drive_link"),
        "vikash.cse.24@nitap.ac.in" to StudentOE("MH-310X", "Innovation, Technology and Entrepreneurship", "M.K.Shome", "CS block", "https://drive.google.com/file/d/1uWNbml9nzmTG2jtqFxDC7kRN-MwLIJTy/view?usp=drive_link"),
        "vinay.cse.24@nitap.ac.in" to StudentOE("MH-310X", "Innovation, Technology and Entrepreneurship", "M.K.Shome", "CS block", "https://drive.google.com/file/d/1uWNbml9nzmTG2jtqFxDC7kRN-MwLIJTy/view?usp=drive_link"),

        // Electrical Machines (EE-310X)
        "jainesh.cse.24@nitap.ac.in" to StudentOE("EE-310X", "Electrical Machines", "Abhik banarjee", "Electrical Block", "https://drive.google.com/file/d/1eUlvOid30N7p_kA9OckBaqurSE0AyOvv/view?usp=drive_link"),
        "kallepalli.cse.24@nitap.ac.in" to StudentOE("EE-310X", "Electrical Machines", "Abhik banarjee", "Electrical Block", "https://drive.google.com/file/d/1eUlvOid30N7p_kA9OckBaqurSE0AyOvv/view?usp=drive_link"),
        "mohammed.cse.24@nitap.ac.in" to StudentOE("EE-310X", "Electrical Machines", "Abhik banarjee", "Electrical Block", "https://drive.google.com/file/d/1eUlvOid30N7p_kA9OckBaqurSE0AyOvv/view?usp=drive_link"),
        "pathina.cse.24@nitap.ac.in" to StudentOE("EE-310X", "Electrical Machines", "Abhik banarjee", "Electrical Block", "https://drive.google.com/file/d/1eUlvOid30N7p_kA9OckBaqurSE0AyOvv/view?usp=drive_link"),
        "ramavath.cse.24@nitap.ac.in" to StudentOE("EE-310X", "Electrical Machines", "Abhik banarjee", "Electrical Block", "https://drive.google.com/file/d/1eUlvOid30N7p_kA9OckBaqurSE0AyOvv/view?usp=drive_link"),
        "sandeep.cse.24@nitap.ac.in" to StudentOE("EE-310X", "Electrical Machines", "Abhik banarjee", "Electrical Block", "https://drive.google.com/file/d/1eUlvOid30N7p_kA9OckBaqurSE0AyOvv/view?usp=drive_link"),
        "tantapureddy.cse.24@nitap.ac.in" to StudentOE("EE-310X", "Electrical Machines", "Abhik banarjee", "Electrical Block", "https://drive.google.com/file/d/1eUlvOid30N7p_kA9OckBaqurSE0AyOvv/view?usp=drive_link"),

        // Missing OE
        "gorle.cse.24@nitap.ac.in" to StudentOE("", "", "", "", "")
    )

    /**
     * Look up the student's Open Elective III based on their Firebase email.
     * Robust against accidental leading/trailing spaces.
     */
    fun getElectiveForUser(email: String): StudentOE? {
        return mapping[email.trim()]
    }
}
