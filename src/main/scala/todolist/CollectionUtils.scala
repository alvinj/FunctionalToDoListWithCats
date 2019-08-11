package todolist

object CollectionUtils {

    def removeElementFromSequence(seq: Seq[String], index: Int): Seq[String] = {
        if (index < 0 || index >= seq.length) {
            seq
        } else if (index == 0) {
            seq.tail
        } else {
            val (a, b) = seq.splitAt(index)
            a ++ b.tail
        }
    }
    
}

