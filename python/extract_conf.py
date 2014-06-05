import sys

if __name__ == '__main__':
    if len(sys.argv) != 3:
        print 'usage: <conf> <param>'
        sys.exit(1)

    res = None
    param = sys.argv[2]
    for line in open(sys.argv[1]):
        ele = line.split('=')
        if len(ele) == 2 and ele[0].strip() == param:
            assert res is None, "duplicate definition of %s" % param
            res = ele[1].strip().strip('"')

    res = "" if res is None else res
    sys.stdout.write(res)
                
